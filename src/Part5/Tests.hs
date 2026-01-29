module Part5.Tests where

import Test.Tasty.HUnit

import Part5.Tasks
import Util

unit_myFoldl = do
    myFoldl (+) 0 [1, 2, 3] @?= 6

prop_myFoldl :: Int -> [Int] -> Bool
prop_myFoldl a as = myFoldl (+) a as == foldl (+) a as

unit_myFoldr = do
    myFoldr (+) 0 [1, 2, 3] @?= 6

prop_myFoldr :: String -> [String] -> Bool
prop_myFoldr a as = myFoldr (++) a as == foldr (++) a as

unit_myMap = do
    assertEqual "" [] (myMap (+45) [])
    assertEqual "" [2,3,4] (myMap (+1) [1,2,3])

prop_myMap :: String -> [String] -> Bool
prop_myMap suffix lst = myMap (++suffix) lst == map (++suffix) lst

unit_myConcatMap = do
    assertEqual ""  [1,2,3,2,3,4,3,4,5] (myConcatMap (\x -> [x, x+1, x+2]) [1, 2, 3])
    assertEqual "" [1,2,3] (myConcatMap (\x -> [x]) [1, 2, 3])
    assertEqual "" [1,3,5,7,9] (myConcatMap (\x -> [1,x..10]) [3])

prop_myConcatMap :: String -> [String] -> Bool
prop_myConcatMap suffix lst = myConcatMap (++suffix) lst == concatMap (++suffix) lst

unit_myReverse = do
    assertEqual "" empty (myReverse empty)
    assertEqual "" [1] (myReverse [1])
    assertEqual "" [1,2,3] (myReverse [3, 2, 1])
        where empty = [] :: [Int]

prop_myReverse lst = myReverse (myReverse lst) == lst
    where types = lst :: [String]

unit_myConcat = do
    "abc" @?= myConcat ["a", "b", "c"]
    "ab" @?= myConcat ["a", "", "b", ""]
    "" @?= myConcat ["", ""]
    "" @?= myConcat []

prop_myConcat :: [[Int]] -> Bool
prop_myConcat lst = myConcat lst == concat lst

unit_myFilter = do
    myFilter (`elem` "123456789") "" @?= ""
    myFilter (`elem` "123456789") "a1v234asas5" @?= "12345"

prop_myFilter :: [Int] -> Bool
prop_myFilter lst = myFilter (\x -> x `mod` 2 == 1) lst == filter (\x -> x `mod` 2 == 1) lst

prop_myPartition :: [Int] -> Bool
prop_myPartition lst =
    let pred x = x `mod` 2 == 1 in
    myPartition pred lst == (filter pred lst, filter (not.pred) lst)
