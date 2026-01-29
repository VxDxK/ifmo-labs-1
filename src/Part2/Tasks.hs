module Part2.Tasks where

import Util (notImplementedYet)

data BinaryOp = Plus | Minus | Times deriving (Show, Eq)

data Term
  = IntConstant {intValue :: Int} -- числовая константа
  | Variable {varName :: String} -- переменная
  | BinaryTerm {op :: BinaryOp, lhv :: Term, rhv :: Term} -- бинарная операция
  deriving (Show, Eq)

-- Для бинарных операций необходима не только реализация, но и адекватные
-- ассоциативность и приоритет
(|+|) :: Term -> Term -> Term
(|+|) l r = BinaryTerm Plus l r

(|-|) :: Term -> Term -> Term
(|-|) l r = BinaryTerm Minus l r

(|*|) :: Term -> Term -> Term
(|*|) l r = BinaryTerm Times l r

infixl 6 |+|, |-|

infixl 7 |*|

-- Заменить переменную `varName` на `replacement`
-- во всём выражении `expression`
replaceVar :: String -> Term -> Term -> Term
replaceVar varName replacement (Variable name)
  | name == varName = replacement
  | otherwise = Variable name
replaceVar varName replacement (BinaryTerm op l r) =
  BinaryTerm
    op
    (replaceVar varName replacement l)
    (replaceVar varName replacement r)

-- Посчитать значение выражения `Term`
-- если оно состоит только из констант
evaluate :: Term -> Term
evaluate (IntConstant n) = IntConstant n
evaluate (Variable name) = Variable name
evaluate (BinaryTerm op l r) =
  case (evaluate l, evaluate r) of
    (IntConstant a, IntConstant b) -> IntConstant (applyOp op a b)
    (l', r') -> BinaryTerm op l' r'
  where
    applyOp Plus a b = a + b
    applyOp Minus a b = a - b
    applyOp Times a b = a * b
