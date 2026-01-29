module Part3.Tests where

import Test.Tasty.HUnit
import Test.Tasty.QuickCheck
import Part3.Tasks

import Data.List

unit_finc = do
    take 30 (finc (+1) 0) @?= [1..30]
    take 30 (finc id 0) @?= [0..29]

unit_ff = do
    take 30 (ff (+1) 0) @?= [0..29]
    take 30 (ff id 0) @?= replicate 30 0

unit_mostFreq = do
    mostFreq [1] @?= 1
    mostFreq [11, 12] @?= 1
    mostFreq [33, 3, 992, 99] @?= 9

unit_uniq = do
    uniq ([]::[Int]) @?= []
    uniq [1,1,1,1] @?= [1]
    sort (uniq [1,1,2,1,3,5,3,2]) @?= sort [1,2,3,5]

sortySortySort :: (Ord a, Ord b) => [(a, [b])] -> [(a, [b])]
sortySortySort lst = sortBy (\ a b -> compare (fst a) (fst b)) $ map (\ (x, l) -> (x, sort l) ) lst

unit_grokBy = do
    grokBy id ([]::[Int]) @?= []
    sortySortySort (grokBy id [1, 2, 3, 4, 5]) @?= sortySortySort ([(1, [1]), (2, [2]), (3, [3]), (4, [4]), (5, [5])])
    sortySortySort (grokBy (> 0) [-2, -1, 0, 1, 2]) @?= sortySortySort ([(True, [1, 2]), (False, [-2, -1, 0])])
