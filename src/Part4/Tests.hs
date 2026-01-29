module Part4.Tests where


import Test.Tasty.HUnit
import Test.Tasty.QuickCheck
import Part4.Tasks

empty :: ReverseList Int
empty = REmpty

unit_show =
    do
        show (REmpty :< 1 :< 2 :< 3 :< 4 :< 5) @?= "[1,2,3,4,5]"
        show empty @?= "[]"

prop_show lst =
    show (listToRlist lst) == show lst
        where types = (lst :: [Int])

unit_eq =
    do
        (REmpty :< 1 :< 2 :< 3 :< 4 :< 5) @?= (REmpty :< 1 :< 2 :< 3 :< 4 :< 5)
        empty @?= empty
        assertBool "" ((REmpty :< 1 :< 2 :< 3 :< 4 :< 5) /= REmpty )

prop_eq lst =
    (listToRlist lst) == (listToRlist lst)
        where types = (lst :: [Int])

unit_monoid =
    do
        mempty @?= empty
        ((REmpty :< 1 :< 2 :< 3) <> (REmpty :< 4 :< 5 :< 6)) @?= (REmpty :< 1 :< 2 :< 3 :< 4 :< 5 :< 6)

prop_monoid lst =
    (mempty <> lst == lst) .&&. (lst <> mempty == lst) .&&. (empty <> empty == empty)
    where lst' :: ReverseList Int
          lst' = listToRlist lst

unit_functor =
    do
        (+1) <$> empty @?= empty
        (*2) <$> (REmpty :< 1 :< 2 :< 3) @?= (REmpty :< 2 :< 4 :< 6)

unit_applicative =
    do
        (+) <$> empty <*> empty @?= empty
        (*) <$> (REmpty :< 1 :< 2 :< 3) <*> (REmpty :< 2) @?= (REmpty :< 2 :< 4 :< 6)
        (+) <$> (REmpty :< 1 :< 2) <*> (REmpty :< 3 :< 4) @?= (REmpty :< 4 :< 5 :< 5 :< 6)
        -- additional check for above expression
        show (fmap (+) (listToRlist [1,2]) <*> (listToRlist [3,4])) @?= show (fmap (+) [1,2] <*> [3,4])

prop_applicative =
    oneArg .&&. twoArg
    where
        rpure :: a -> ReverseList a
        rpure = pure
        oneArg (Fun _ f) val =
            (f <$> rpure val) == rpure (f val)
                where types = (val :: Int, f :: Int -> Int)
        twoArg (Fun _ f) v1 v2 =
            (f' <$> rpure v1 <*> rpure v2) == rpure ( f (v1,v2) )
                where
                    f' x y = f (x, y)
                    types = (v1 :: Int, v2 :: Int, f :: (Int, Int) -> Int)

unit_monad =
    do
        return 1 @?= (REmpty :< 1)
        (do { x <- REmpty :< 1 :< 2; y <- REmpty :< 3 :< 4; return (x + y) }) @?=
            (REmpty :< 4 :< 5 :< 5 :< 6) -- same fix as above (line 49)
        (do { x <- REmpty :< 1 :< 2; y <- REmpty; return (x + y) }) @?=
            (REmpty)
