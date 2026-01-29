module Part4.Tasks where

import Util(notImplementedYet)

-- Перевёрнутый связный список -- хранит ссылку не на последующию, а на предыдущую ячейку
data ReverseList a = REmpty | (ReverseList a) :< a
infixl 5 :<

-- Функция-пример, делает из перевёрнутого списка обычный список
-- Использовать rlistToList в реализации классов запрещено =)
rlistToList :: ReverseList a -> [a]
rlistToList lst =
    reverse (reversed lst)
    where reversed REmpty = []
          reversed (init :< last) = last : reversed init

-- Реализуйте обратное преобразование
listToRlist :: [a] -> ReverseList a
listToRlist l = listToRlistImpl l REmpty
    where
        listToRlistImpl [] a = a
        listToRlistImpl (x:xs) a = listToRlistImpl xs (a :< x)

-- Реализуйте все представленные ниже классы (см. тесты)
instance Show a => Show (ReverseList a) where
    showsPrec _ REmpty = showString "[]"
    showsPrec _ xs =
        showChar '[' . (show' xs) . showChar ']'
        where
            show' (REmpty :< x) = shows x
            show' (xs :< x)     = show' xs . showChar ',' . shows x

    show = ($ "") . showsPrec 0

instance Eq a => Eq (ReverseList a) where
    REmpty == REmpty   = True
    ls :< l == rs :< r = l == r && ls == rs
    _ == _             = False

instance Semigroup (ReverseList a) where
    lhv <> REmpty    = lhv
    lhv <> (rs :< r) = (lhv <> rs) :< r

instance Monoid (ReverseList a) where
    mempty = REmpty

instance Functor ReverseList where
    fmap _ REmpty    = REmpty
    fmap f (xs :< x) = fmap f xs :< f x

instance Applicative ReverseList where
    pure x         = REmpty :< x
    REmpty  <*> _  = REmpty
    fs :< f <*> xs = (fs <*> xs) <> fmap f xs

instance Monad ReverseList where
    REmpty >>= _    = REmpty
    (xs :< x) >>= f = (xs >>= f) <> (f x)