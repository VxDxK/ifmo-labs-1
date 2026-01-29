{-# Language ExistentialQuantification  #-} -- Разрешает forall (см. Inject)
{-# Language StandaloneDeriving         #-} -- Разрешает deriving instance
{-# Language OverloadedStrings          #-} -- Разрешает произвольные типы для строк в кавычках
{-# Language ScopedTypeVariables        #-} -- Разрещает локально указывать типы
{-# Language ConstraintKinds            #-} -- Нужно для рефлексии (см. typeAgnosticEqual)
{-# Language GADTs                      #-} -- Нужно для рефлексии (см. typeAgnosticEqual)
module Lambdas where

-- Реализация лямбда-исчисления из лекции про лямбда-исчисление в виде DSL на Haskell
-- Приводится здесь исключительно как иллюстративный пример кода на Haskell с расширениями

import Data.String (IsString(..))
import Type.Reflection

-- Сравнивает два значения разных типов на равенство, do not try this at home
typeAgnosticEqual :: forall a b. (Eq a, Eq b, Typeable a, Typeable b) => a -> b -> Bool
typeAgnosticEqual x y =
  case eqTypeRep (typeRep :: TypeRep a) (typeRep :: TypeRep b) of
    Just HRefl -> x == y
    Nothing -> False

-- Выражение лямбда-исчисления
data Expression =
        Expression :-> Expression -- lambda
    |   Expression :$: Expression -- apply
    |   Var String -- variable
    |   forall a. (Show a, Typeable a, Eq a) => Inject a -- возможность пихать внутрь произвольные объекты из Haskell, just for fun

infixr 0 :->
infixl 1 :$:

-- Красивая печать выражений
-- Не используется для стандартного show, потому что стандартный show нужен для дебага
pprint :: Expression -> String
pprint (v :-> body) = "λ " ++ showArgs v body
                        where showArgs v (v1 :-> body) = concat [pprint v, " ", showArgs v1 body]
                              showArgs v abody = concat [pprint v, ".", pprint abody]
pprint (a :$: b) = concat [showFun a, " ", showArg b]
                    where showFun f@(Var x) = pprint f
                          showFun f@(a :$: b) = pprint f
                          showFun f = concat ["(", pprint f, ")"]
                          showArg f@(Var x) = pprint f
                          showArg f = concat ["(", pprint f, ")"]
pprint (Var x) = x
pprint (Inject x) = show x

-- То же самое, но на консоль
pprintStrln :: Expression -> IO ()
pprintStrln a = putStrLn $ pprint a

deriving instance Show Expression

-- Нестандартный инстанс для Eq
-- Только чтобы использовать typeAgnosticEquals для Inject
instance Eq Expression where
    Var x == Var y | x == y = True
    (f1 :$: a1) == (f2 :$: a2) | f1 == f2 && a1 == a2 = True
    (v1 :-> body1) == (v2 :-> body2) | v1 == v2 && body1 == body2 = True
    Inject a == Inject b = typeAgnosticEqual a b
    _ == _ = False

-- Нужно, чтобы использовать строки в кавычках как Expression
instance IsString Expression where
    fromString s = Var s

-- Замена подвыражения внутри выражения
replace :: Expression -> Expression -> Expression -> Expression
replace what withWhat inWhat =
    replace' what inWhat
    where replace' (Var x) v@(Var y) | x == y = withWhat
                                     | otherwise = v
          replace' x (f :$: a) = replace' x f :$: replace' x a
          replace' x orig@(v :-> b) | x == v = orig
                                    | otherwise = replace' x v :-> replace' x b
          replace' _ v = v

-- Вычисление выражения (если можно вычислить)
eval :: Expression -> Expression
eval original@(a :$: b) = eval' (eval a) (eval b)
    where
        eval' (v :-> body) a = eval $ replace v a body
        eval' x y | x == a && y == b = original
                  | otherwise        = eval (x :$: y)
eval orig = orig

-- Попытаться интерпретировать выражение как булевское
reifyAsBool :: Expression -> Maybe Bool
reifyAsBool e = reify' (eval e)
    where reify' f@(x :-> y :-> body) =
            let res = eval (f :$: Inject True :$: Inject False) in
                case res of
                     Inject _ | res == Inject True -> Just True
                              | res == Inject False -> Just False
                     _ -> Nothing
          reify' _ = Nothing

data Succ = Succ deriving (Show, Eq, Typeable)

-- Попытаться интерпретировать выражение как число
reifyAsInteger :: Expression -> Maybe Integer
reifyAsInteger e = reify' (eval e)
    where reify' n =
            let zero :: Integer
                zero = 0
                res = eval (n :$: Inject Succ :$: Inject zero)
                reify'' f@Inject{} | f == Inject zero = Just 0
                reify'' (f@Inject{} :$: rest) | f == Inject Succ = (+1) <$> reify'' rest
                reify'' _ = Nothing
            in reify'' res

-- stdlib
-- большая часть функций из лекции
pair :: Expression -- pair x y f = f x y
pair = "x" :-> "y" :-> "f" :-> "f" :$: "x" :$: "y"
fst :: Expression -- fst f = f (\x _ . x)
fst = "f" :-> "f" :$: ("x" :-> "_" :-> "x")
snd :: Expression -- snd f = f (\_ y . y)
snd = "f" :-> "f" :$: ("_" :-> "y" :-> "y")

false :: Expression -- false _ y = y
false = "_" :-> "y" :-> "y"
true :: Expression -- true x _ = x
true = "x" :-> "_" :-> "x"
iff :: Expression -- iff c t f = c t f
iff = "c" :-> "t" :-> "f" :-> "c" :$: "t" :$: "f"
not :: Expression -- not x = x false true
not = "x" :-> "x" :$: false :$: true
and :: Expression -- and x y = x y false
and = "x" :-> "y" :-> "x" :$: "y" :$: false
or :: Expression -- or x y = x true y
or = "x" :-> "y" :-> "x" :$: true :$: "y"

zero :: Expression -- 0 f x = x
zero = "f" :-> "x" :-> "x"
succ :: Expression -- succ n f x = f (n f x)
succ = "n" :-> "f" :-> "x" :-> "f" :$: ("n" :$: "f" :$: "x")
one :: Expression -- 1 = succ 0
one = Lambdas.succ :$: zero
two :: Expression -- 2 = succ 1
two = Lambdas.succ :$: one
three :: Expression -- 3 = succ 2
three = Lambdas.succ :$: two
four :: Expression -- 4 = succ 3
four = Lambdas.succ :$: three
isZero :: Expression -- isZero n = n (\_ . false) true
isZero = "n" :-> "n" :$: ("_" :-> false) :$: true
plus :: Expression -- plus m n f z = m f (n f z)
plus = "m" :-> "n" :-> "f" :-> "z" :-> "m" :$: "f" :$: ("n" :$: "f" :$: "z")
times :: Expression -- times m n = n (plus m) 0
times = "m" :-> "n" :-> "n" :$: (plus :$: "m") :$: zero
pred :: Expression -- pred n f z = n (\ g h . h (g h)) (\_ . z) (\u . u)
pred = "n" :-> "f" :-> "z" :-> "n" :$: ("g" :-> "h" :-> "h" :$: ("g" :$: "h")) :$: ("_" :-> "z") :$: ("u" :-> "u")
minus :: Expression -- minus m n = n pred m
minus = "m" :-> "n" :-> "n" :$: Lambdas.pred :$: "m"
pow :: Expression -- pow m n f x = n m f x
pow = "m" :-> "n" :-> "f" :-> "x" :-> "n" :$: "m" :$: "f" :$: "x"

-- Как этим пользоваться, например, в интерпретаторе:
-- > reifyAsInteger $ pow :$: three :$: four
-- > Just 81
