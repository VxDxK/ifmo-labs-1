#!/usr/bin/env python3
import sys
import os
import subprocess
from clang.cindex import Index, CursorKind


class Block:
    counter = 0

    def __init__(self):
        self.id = Block.counter
        Block.counter += 1
        self.stmts = []
        self.successors = []


class CFGBuilder:
    def __init__(self):
        self.blocks = []
        self.entry = None
        self.exit = None
        self.label_to_block = {}
        self.backpatch_goto = []
        self.break_target = None
        self.continue_target = None
        self.case_targets = None

    def new_block(self):
        b = Block()
        self.blocks.append(b)
        return b

    def generate_dot(self):
        dot = 'digraph {\n'
        for b in self.blocks:
            label = '\\n'.join(b.stmts) if b.stmts else 'empty'
            if label == 'Start' or label == 'End':
                shape = 'ellipse'
            elif label.endswith('?'):
                shape = 'diamond'
            else:
                shape = 'box'
            dot += f' B{b.id} [shape={shape} label="{label}"];\n'
        for b in self.blocks:
            for succ, label in b.successors:
                l = f' [label="{label}"]' if label else ''
                dot += f' B{b.id} -> B{succ}{l};\n'
        dot += '}\n'
        return dot

    def optimize(self):
        id_to_block = {b.id: b for b in self.blocks}
        preds = {b.id: [] for b in self.blocks}
        for b in self.blocks:
            for succ, _ in b.successors:
                preds[succ].append(b.id)
        changed = True
        while changed:
            changed = False
            for b in list(self.blocks):
                if b.stmts or b == self.entry or b == self.exit:
                    continue
                if not b.successors:
                    continue
                for p_id in list(preds[b.id]):
                    if p_id not in id_to_block:
                        continue
                    p = id_to_block[p_id]
                    new_succ = []
                    for s, l in p.successors:
                        if s == b.id:
                            for ss, ll in b.successors:
                                label = ll if ll else l
                                new_succ.append((ss, label))
                        else:
                            new_succ.append((s, l))
                    p.successors = new_succ
                for ss, _ in b.successors:
                    preds[ss].extend([p for p in preds[b.id] if p in id_to_block])
                preds[b.id] = []
                self.blocks.remove(b)
                del id_to_block[b.id]
                del preds[b.id]
                changed = True
                break
        preds = {b.id: [] for b in self.blocks}
        for b in self.blocks:
            for succ, _ in b.successors:
                if succ in preds:
                    preds[succ].append(b.id)
        for b in list(self.blocks):
            if b != self.entry and b != self.exit and not preds.get(b.id, []):
                self.blocks.remove(b)
                del id_to_block[b.id]
                del preds[b.id]

    def build_cfg(self, func, source):
        children = list(func.get_children())
        body = children[-1] if children and children[-1].kind == CursorKind.COMPOUND_STMT else None
        if not body:
            return 'no body'
        self.blocks = []
        self.exit = self.new_block()
        self.exit.stmts = ['End']
        self.entry = self.new_block()
        self.entry.stmts = ['Start']
        self.label_to_block = {}
        self.backpatch_goto = []
        self.break_target = None
        self.continue_target = None
        self.case_targets = None
        code_start = self.new_block()
        self.entry.successors.append((code_start.id, ''))
        current = self.process_body(body, code_start, source)
        current.successors.append((self.exit.id, ''))
        for curr_block, label_name in self.backpatch_goto:
            if label_name in self.label_to_block:
                target = self.label_to_block[label_name]
                curr_block.successors.append((target.id, 'goto'))
        self.optimize()
        return self.generate_dot()

    def get_source(self, cursor, source):
        start = cursor.extent.start.offset
        end = cursor.extent.end.offset
        return source[start:end].strip()

    def process_stmts(self, stmts, current, source):
        for stmt in stmts:
            kind = stmt.kind
            if kind == CursorKind.IF_STMT:
                children = list(stmt.get_children())
                cond = children[0]
                then_body = children[1]
                else_body = children[2] if len(children) == 3 else None
                cond_block = self.new_block() if current.stmts else current
                if current.stmts:
                    current.successors.append((cond_block.id, ''))
                current = cond_block
                current.stmts.append(self.get_source(cond, source) + '?')
                then_entry = self.new_block()
                then_exit = self.process_body(then_body, then_entry, source)
                current.successors.append((then_entry.id, 'true'))
                if else_body:
                    else_entry = self.new_block()
                    else_exit = self.process_body(else_body, else_entry, source)
                    current.successors.append((else_entry.id, 'false'))
                    merge = self.new_block()
                    then_exit.successors.append((merge.id, ''))
                    else_exit.successors.append((merge.id, ''))
                else:
                    merge = self.new_block()
                    then_exit.successors.append((merge.id, ''))
                    current.successors.append((merge.id, 'false'))
                current = merge
            elif kind == CursorKind.FOR_STMT:
                children = list(stmt.get_children())
                init = children[0] if len(children) > 0 else None
                cond = children[1] if len(children) > 1 else None
                inc = children[2] if len(children) > 2 else None
                body = children[-1]
                if init:
                    init_block = self.new_block() if current.stmts else current
                    if current.stmts:
                        current.successors.append((init_block.id, ''))
                    current = init_block
                    current.stmts.append(self.get_source(init, source))
                header = self.new_block()
                current.successors.append((header.id, ''))
                if cond:
                    header.stmts.append(self.get_source(cond, source) + '?')
                else:
                    header.stmts.append('true?')
                merge = self.new_block()
                header.successors.append((merge.id, 'false'))
                body_entry = self.new_block()
                header.successors.append((body_entry.id, 'true'))
                old_break_target = self.break_target
                old_continue_target = self.continue_target
                self.break_target = merge
                inc_block = self.new_block()
                self.continue_target = inc_block
                body_exit = self.process_body(body, body_entry, source)
                body_exit.successors.append((inc_block.id, ''))
                if inc:
                    inc_block.stmts.append(self.get_source(inc, source))
                inc_block.successors.append((header.id, ''))
                self.break_target = old_break_target
                self.continue_target = old_continue_target
                current = merge
            elif kind == CursorKind.WHILE_STMT:
                children = list(stmt.get_children())
                cond = children[0]
                body = children[1]
                header = self.new_block()
                current.successors.append((header.id, ''))
                header.stmts.append(self.get_source(cond, source) + '?')
                merge = self.new_block()
                header.successors.append((merge.id, 'false'))
                body_entry = self.new_block()
                header.successors.append((body_entry.id, 'true'))
                old_break_target = self.break_target
                old_continue_target = self.continue_target
                self.break_target = merge
                self.continue_target = header
                body_exit = self.process_body(body, body_entry, source)
                body_exit.successors.append((header.id, ''))
                self.break_target = old_break_target
                self.continue_target = old_continue_target
                current = merge
            elif kind == CursorKind.DO_STMT:
                children = list(stmt.get_children())
                body = children[0]
                cond = children[1]
                body_entry = self.new_block()
                current.successors.append((body_entry.id, ''))
                header = self.new_block()
                merge = self.new_block()
                old_break_target = self.break_target
                old_continue_target = self.continue_target
                self.break_target = merge
                self.continue_target = header
                body_exit = self.process_body(body, body_entry, source)
                body_exit.successors.append((header.id, ''))
                header.stmts.append(self.get_source(cond, source) + '?')
                header.successors.append((body_entry.id, 'true'))
                header.successors.append((merge.id, 'false'))
                self.break_target = old_break_target
                self.continue_target = old_continue_target
                current = merge
            elif kind == CursorKind.SWITCH_STMT:
                children = list(stmt.get_children())
                cond = children[0]
                body = children[1]
                switch_block = self.new_block() if current.stmts else current
                if current.stmts:
                    current.successors.append((switch_block.id, ''))
                current = switch_block
                merge = self.new_block()
                old_break_target = self.break_target
                self.break_target = merge
                self.case_targets = []
                body_entry = self.new_block()
                body_exit = self.process_body(body, body_entry, source)
                case_list = [(v, b) for v, b in self.case_targets if v != 'default']
                default_b = next((b for v, b in self.case_targets if v == 'default'), None)
                has_default = default_b is not None
                current.successors = []
                if not case_list:
                    if has_default:
                        current.successors.append((default_b.id, ''))
                    else:
                        current.successors.append((merge.id, ''))
                else:
                    first_check = self.new_block()
                    first_val = case_list[0][0]
                    first_check.stmts.append(f'({self.get_source(cond, source)}) == {first_val} ?')
                    current.successors.append((first_check.id, ''))
                    first_check.successors.append((case_list[0][1].id, 'true'))
                    last_false = first_check
                    for value, target in case_list[1:]:
                        next_check = self.new_block()
                        next_check.stmts.append(f'({self.get_source(cond, source)}) == {value} ?')
                        last_false.successors.append((next_check.id, 'false'))
                        next_check.successors.append((target.id, 'true'))
                        last_false = next_check
                    if has_default:
                        last_false.successors.append((default_b.id, 'false'))
                    else:
                        last_false.successors.append((merge.id, 'false'))
                body_exit.successors.append((merge.id, ''))
                self.break_target = old_break_target
                self.case_targets = None
                current = merge
            elif kind == CursorKind.CASE_STMT:
                value_cursor = list(stmt.get_children())[0]
                sub_stmt = list(stmt.get_children())[1]
                value = self.get_source(value_cursor, source)
                self.case_targets.append((value, current))
                current = self.process_stmts([sub_stmt], current, source)
            elif kind == CursorKind.DEFAULT_STMT:
                sub_stmt = list(stmt.get_children())[0]
                self.case_targets.append(('default', current))
                current = self.process_stmts([sub_stmt], current, source)
            elif kind == CursorKind.BREAK_STMT:
                if self.break_target:
                    current.successors.append((self.break_target.id, ''))
                current = self.new_block()
            elif kind == CursorKind.CONTINUE_STMT:
                if self.continue_target:
                    current.successors.append((self.continue_target.id, ''))
                current = self.new_block()
            elif kind == CursorKind.GOTO_STMT:
                stmt_block = self.new_block() if current.stmts else current
                if current != stmt_block:
                    current.successors.append((stmt_block.id, ''))
                current = stmt_block
                current.stmts.append(self.get_source(stmt, source))
                label_cursor = list(stmt.get_children())[0]
                label_name = label_cursor.spelling
                if label_name in self.label_to_block:
                    current.successors.append((self.label_to_block[label_name].id, 'goto'))
                else:
                    self.backpatch_goto.append((current, label_name))
                current = self.new_block()
            elif kind == CursorKind.LABEL_STMT:
                label_name = stmt.spelling
                sub_stmt = list(stmt.get_children())[0]
                self.label_to_block[label_name] = current
                current = self.process_stmts([sub_stmt], current, source)
            elif kind == CursorKind.RETURN_STMT:
                children = list(stmt.get_children())
                if children:
                    expr_block = self.new_block() if current.stmts else current
                    if current != expr_block:
                        current.successors.append((expr_block.id, ''))
                    current = expr_block
                    current.stmts.append(self.get_source(children[0], source))
                current.successors.append((self.exit.id, ''))
                current = self.new_block()
            else:
                stmt_block = self.new_block() if current.stmts else current
                if current != stmt_block:
                    current.successors.append((stmt_block.id, ''))
                current = stmt_block
                current.stmts.append(self.get_source(stmt, source))
        return current

    def process_body(self, body, current, source):
        if body.kind == CursorKind.COMPOUND_STMT:
            stmts = list(body.get_children())
        else:
            stmts = [body]
        return self.process_stmts(stmts, current, source)


def main(cpp_file):
    index = Index.create()
    tu = index.parse(cpp_file, args=['-std=c++17'])
    source = open(cpp_file, 'r').read()
    functions = []

    def traverse(cursor):
        if cursor.kind == CursorKind.FUNCTION_DECL and cursor.is_definition() and cursor.location.file.name == cpp_file:
            functions.append(cursor)
        for child in cursor.get_children():
            traverse(child)

    traverse(tu.cursor)
    os.makedirs("cfgs", exist_ok=True)
    for func in functions:
        builder = CFGBuilder()
        dot = builder.build_cfg(func, source)
        dot_path = f"cfgs/{func.spelling}.dot"
        png_path = f"cfgs/{func.spelling}.png"
        with open(dot_path, 'w') as f:
            f.write(dot)
        subprocess.call(['dot', '-Tpng', dot_path, '-o', png_path])


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python cfg_builder.py <cpp_file>")
        sys.exit(1)
    main(sys.argv[1])
