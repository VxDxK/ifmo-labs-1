module Part2.Tests where

import Test.Tasty.HUnit
import Test.Tasty.QuickCheck
import Part2.Tasks

unit_term_ops = do
    (IntConstant 2 |+| Variable "x" |-| Variable "y") @?=
        (BinaryTerm Minus (BinaryTerm Plus (IntConstant 2) (Variable "x")) (Variable "y"))
    (IntConstant 2 |+| Variable "x" |+| Variable "y") @?=
        (BinaryTerm Plus (BinaryTerm Plus (IntConstant 2) (Variable "x")) (Variable "y"))
    (IntConstant 2 |+| Variable "x" |*| Variable "y") @?=
        (BinaryTerm Plus (IntConstant 2) (BinaryTerm Times (Variable "x") (Variable "y")))
    (Variable "x" |+| Variable "y" |*| Variable "x" |+| Variable "y") @?=
        (Variable "x" |+| (Variable "y" |*| Variable "x") |+| Variable "y")

unit_replaceVar = do
    (replaceVar "x" (IntConstant 2) (Variable "x")) @?= IntConstant 2
    (replaceVar "y" (IntConstant 2) (Variable "x")) @?= Variable "x"
    (replaceVar "x" (Variable "z") (
        replaceVar "y" (Variable "p") (
            Variable "x" |+| Variable "y" |*| Variable "x" |+| Variable "y"))) @?=
        (Variable "z" |+| Variable "p" |*| Variable "z" |+| Variable "p")

prop_evaluate x y z =
    let x' = IntConstant x
        y' = IntConstant y
        z' = IntConstant z
    in
    (evaluate (x' |+| y') == IntConstant (x + y)) .&&.
        (evaluate (x' |+| Variable "x" |+| y') == (x' |+| Variable "x" |+| y')) .&&.
        (evaluate (x' |*| y' |+| z' |*| x') == IntConstant (x * y + z * x))


