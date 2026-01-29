module Part6.Tests where

import qualified Data.Map

import Test.Tasty.HUnit
import Test.Tasty.QuickCheck
import Part6.Tasks

unit_eye = do
    eye 1 @?= one
    eye 1 @?= [[one]]
    eye 1 @?= SparseMatrix 1 1 (Data.Map.fromList [((0, 0), one)])
    eye 2 @?= [[one, 0], [0, one]]
    eye 2 @?= SparseMatrix 2 2 (Data.Map.fromList [((0, 0), one), ((1, 1), one)])

    where one :: Int; one = 1

unit_zero = do
    zero 1 1 @?= zz
    zero 2 1 @?= [[zz, zz]]
    zero 2 2 @?= [[zz, zz], [zz, zz]]
    zero 5 5 @?= SparseMatrix 5 5 (Data.Map.fromList ([]::[((Int, Int), Int)]))
    where zz :: Int; zz = 0

