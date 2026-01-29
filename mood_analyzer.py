#!/usr/bin/env python3

import sys
import os
from typing import Dict, List, Set, Tuple, Optional
from collections import defaultdict
from clang.cindex import Index, TranslationUnit, Cursor, CursorKind, AccessSpecifier
from tabulate import tabulate


class ClassInfo:
    def __init__(self, name: str):
        self.name = name
        self.methods: List[Tuple[str, bool, bool, bool]] = []
        self.attributes: List[Tuple[str, bool, bool]] = []
        self.base_classes: List[str] = []
        self.derived_classes: List[str] = []
        self.coupled_classes: Set[str] = set()


class MOODAnalyzer:
    def __init__(self):
        self.classes: Dict[str, ClassInfo] = {}
        self.all_methods: Set[str] = set()
        self.all_attributes: Set[str] = set()
    
    def parse_file(self, file_path: str) -> None:
        index = Index.create()
        tu = index.parse(file_path, args=['-std=c++17', '-x', 'c++'])
        
        if tu.diagnostics:
            print(f"Warnings/Errors in {file_path}:", file=sys.stderr)
            for diag in tu.diagnostics:
                print(f"  {diag}", file=sys.stderr)
        
        self._extract_classes(tu.cursor, file_path)
    
    def _get_current_class(self, cursor: Cursor) -> Optional[str]:
        parent = cursor.semantic_parent
        while parent:
            if parent.kind in [CursorKind.CLASS_DECL, CursorKind.STRUCT_DECL]:
                return parent.spelling
            parent = parent.semantic_parent
        return None
    
    def _process_method_body(self, cursor: Cursor, current_class: str, class_info: ClassInfo) -> None:
        for child in cursor.get_children():
            if child.kind == CursorKind.CALL_EXPR:
                ref_cursor = child.referenced
                if ref_cursor:
                    owner_class = self._get_current_class(ref_cursor)
                    if owner_class and owner_class != current_class:
                        class_info.coupled_classes.add(owner_class)
            elif child.kind == CursorKind.MEMBER_REF_EXPR:
                ref_cursor = child.referenced
                if ref_cursor:
                    owner_class = self._get_current_class(ref_cursor)
                    if owner_class and owner_class != current_class:
                        class_info.coupled_classes.add(owner_class)
            self._process_method_body(child, current_class, class_info)
    
    def _extract_classes(self, cursor: Cursor, file_path: str) -> None:
        if cursor.location.file and cursor.location.file.name != file_path:
            return
        if cursor.kind in [CursorKind.CLASS_DECL, CursorKind.STRUCT_DECL]:
            class_name = cursor.spelling
            if not class_name:
                return
            
            if class_name not in self.classes:
                self.classes[class_name] = ClassInfo(class_name)
            
            class_info = self.classes[class_name]
            current_class = class_name
            for child in cursor.get_children():
                if child.kind == CursorKind.CXX_BASE_SPECIFIER:
                    base_name = child.spelling
                    if not base_name:
                        base_type = child.type
                        if base_type:
                            base_name = base_type.get_declaration().spelling
                    if base_name:
                        class_info.base_classes.append(base_name)
                        if base_name not in self.classes:
                            self.classes[base_name] = ClassInfo(base_name)
                        self.classes[base_name].derived_classes.append(class_name)
                elif child.kind == CursorKind.CXX_METHOD:
                    method_name = child.spelling
                    if method_name:
                        is_private = child.access_specifier == AccessSpecifier.PRIVATE
                        is_virtual = child.is_virtual_method()
                        is_inherited = False
                        class_info.methods.append((method_name, is_private, is_virtual, is_inherited))
                        self.all_methods.add(f"{class_name}::{method_name}")
                        for param in child.get_arguments():
                            param_type = param.type
                            if param_type:
                                try:
                                    if param_type.kind.name in ['RECORD', 'POINTER', 'LVALUEREFERENCE']:
                                        type_decl = param_type.get_declaration()
                                        if type_decl and type_decl.kind in [CursorKind.CLASS_DECL, CursorKind.STRUCT_DECL]:
                                            param_class = type_decl.spelling
                                            if param_class and param_class != current_class:
                                                class_info.coupled_classes.add(param_class)
                                except:
                                    pass
                        self._process_method_body(child, current_class, class_info)
                elif child.kind == CursorKind.FIELD_DECL:
                    attr_name = child.spelling
                    if attr_name:
                        is_private = child.access_specifier == AccessSpecifier.PRIVATE
                        is_inherited = False
                        class_info.attributes.append((attr_name, is_private, is_inherited))
                        self.all_attributes.add(f"{class_name}::{attr_name}")
                elif child.kind == CursorKind.MEMBER_REF_EXPR:
                    ref_cursor = child.referenced
                    if ref_cursor:
                        owner_class = self._get_current_class(ref_cursor)
                        if owner_class and owner_class != current_class:
                            class_info.coupled_classes.add(owner_class)
                elif child.kind == CursorKind.CALL_EXPR:
                    ref_cursor = child.referenced
                    if ref_cursor:
                        owner_class = self._get_current_class(ref_cursor)
                        if owner_class and owner_class != current_class:
                            class_info.coupled_classes.add(owner_class)
        for child in cursor.get_children():
            self._extract_classes(child, file_path)
    
    def _resolve_inheritance(self) -> None:
        def get_all_base_classes(class_name: str, visited: Set[str] = None) -> Set[str]:
            if visited is None:
                visited = set()
            if class_name in visited or class_name not in self.classes:
                return set()
            visited.add(class_name)
            
            bases = set()
            for base_name in self.classes[class_name].base_classes:
                bases.add(base_name)
                bases.update(get_all_base_classes(base_name, visited))
            return bases
        
        for class_name, class_info in self.classes.items():
            all_bases = get_all_base_classes(class_name)
            local_methods = {name for name, _, _, _ in class_info.methods}
            local_attrs = {name for name, _, _ in class_info.attributes}
            for base_name in all_bases:
                if base_name in self.classes:
                    base_info = self.classes[base_name]
                    for method_name, is_private, is_virtual, _ in base_info.methods:
                        if method_name not in local_methods:
                            class_info.methods.append((method_name, is_private, is_virtual, True))
                            local_methods.add(method_name)
                    for attr_name, is_private, _ in base_info.attributes:
                        if attr_name not in local_attrs:
                            class_info.attributes.append((attr_name, is_private, True))
                            local_attrs.add(attr_name)
    
    def calculate_mhf(self) -> float:
        total_hidden = 0
        total_methods = 0
        
        for class_info in self.classes.values():
            for method_name, is_private, _, _ in class_info.methods:
                total_methods += 1
                if is_private:
                    total_hidden += 1
        
        if total_methods == 0:
            return 0.0
        return total_hidden / total_methods
    
    def calculate_ahf(self) -> float:
        total_hidden = 0
        total_attributes = 0
        
        for class_info in self.classes.values():
            for attr_name, is_private, _ in class_info.attributes:
                total_attributes += 1
                if is_private:
                    total_hidden += 1
        
        if total_attributes == 0:
            return 0.0
        return total_hidden / total_attributes
    
    def calculate_mif(self) -> float:
        total_inherited = 0
        total_methods = 0
        for class_name, class_info in self.classes.items():
            print(class_name)
            for method_name, _, _, is_inherited in class_info.methods:
                print(method_name, is_inherited)
                total_methods += 1
                if is_inherited:
                    total_inherited += 1
        
        if total_methods == 0:
            return 0.0
        return total_inherited / total_methods
    
    def calculate_aif(self) -> float:
        total_inherited = 0
        total_attributes = 0
        for class_name, class_info in self.classes.items():
            if class_info.base_classes:
                for attr_name, _, is_inherited in class_info.attributes:
                    total_attributes += 1
                    if is_inherited:
                        total_inherited += 1
        
        if total_attributes == 0:
            return 0.0
        return total_inherited / total_attributes
    
    def calculate_pof(self) -> float:
        numerator = 0
        denominator = 0
        for class_name, class_info in self.classes.items():
            base_method_names = set()
            for base_name in class_info.base_classes:
                if base_name in self.classes:
                    base_info = self.classes[base_name]
                    base_method_names.update(name for name, _, _, _ in base_info.methods)
            mn = 0
            mo = 0
            for method_name, _, _, is_inherited in class_info.methods:
                if is_inherited:
                    continue
                if method_name in base_method_names:
                    mo += 1
                else:
                    mn += 1
            di = len(class_info.derived_classes)
            numerator += mo
            denominator += mn * di
        if denominator == 0:
            return 0.0
        return numerator / denominator
    
    def calculate_cof(self) -> float:
        total_couplings = 0
        max_possible_couplings = len(self.classes) * (len(self.classes) - 1)
        
        for class_info in self.classes.values():
            total_couplings += len(class_info.coupled_classes)
        
        if max_possible_couplings == 0:
            return 0.0
        return total_couplings / max_possible_couplings
    
    def analyze(self, file_path: str) -> Dict[str, float]:
        self.parse_file(file_path)
        self._resolve_inheritance()
        
        metrics = {
            'MHF': self.calculate_mhf(),
            'AHF': self.calculate_ahf(),
            'MIF': self.calculate_mif(),
            'AIF': self.calculate_aif(),
            'POF': self.calculate_pof(),
            'COF': self.calculate_cof()
        }
        
        return metrics
    
    def print_results(self, metrics: Dict[str, float]) -> None:
        table_data = []
        for metric_name, value in metrics.items():
            table_data.append([metric_name, f"{value:.4f}"])
        
        print("\n" + "="*50)
        print("MOOD Metrics Analysis Results")
        print("="*50)
        print(tabulate(table_data, headers=["Metric", "Value"], tablefmt="grid"))
        print("="*50 + "\n")


def main():
    if len(sys.argv) < 2:
        print("Usage: python mood_analyzer.py <cpp_file>")
        sys.exit(1)
    
    file_path = sys.argv[1]
    if not os.path.exists(file_path):
        print(f"Error: File '{file_path}' not found.", file=sys.stderr)
        sys.exit(1)
    
    analyzer = MOODAnalyzer()
    metrics = analyzer.analyze(file_path)
    analyzer.print_results(metrics)


if __name__ == "__main__":
    main()

