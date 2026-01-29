module Part1.Tests where

import Part1.Tasks

import Test.Tasty.HUnit
import Test.Tasty.QuickCheck
import Control.Monad (unless)
import GHC.Stack (HasCallStack)
import Util(assertFalse)

nearlyEqual x y = abs (x - y) <= 0.0001
nearlyEqual' x y =
  counterexample (show x ++ interpret res ++ show y) res
  where
    res = nearlyEqual x y
    interpret True  = " == "
    interpret False = " /= "

infix 4 `nearlyEqual`
infix 4 `nearlyEqual'`

(@?=~) :: (HasCallStack, Ord a, Fractional a, Eq a, Show a) => a -> a -> IO ()
x @?=~ y = unless (nearlyEqual x y) $ assertEqual "" y x

unit_mySin = do
    mySin 0.0 @?=~ 0.0
    mySin pi @?=~ 0.0
    mySin (pi / 2) @?=~ 1.0

prop_mySin v = mySin v `nearlyEqual` sin v

unit_myCos = do
    myCos 0.0 @?=~ 1.0
    myCos pi @?=~ (-1.0)
    myCos (pi / 2) @?=~ 0.0

prop_myCos v = myCos v `nearlyEqual` cos v

unit_myGCD = do
    myGCD 0 1 @?= 1
    myGCD 60 90 @?= 30
    myGCD 12 60 @?= 12
    myGCD 123 145 @?= 1

prop_myGCD i j = myGCD i j === gcd i j


unit_isDateCorrect = do
    isDateCorrect 01 01 1970 @?= True
    isDateCorrect 29 02 1970 @?= False
    isDateCorrect 29 02 1900 @?= False
    isDateCorrect 29 02 2000 @?= True
    isDateCorrect 29 02 2008 @?= True

prop_isDateCorrect year =
    False === isDateCorrect 30 02 year
        .&&. False === isDateCorrect 31 04 year
        .&&. False === isDateCorrect 32 07 year

unit_myPow = do
    myPow 23 1 @?= 23
    myPow 23 0 @?= 1
    myPow 2 10 @?= 1024
    myPow 2 5 @?= 32

prop_myPow =
    zeroExp .&&. oneExp .&&. general
    where zeroExp x = myPow x 0 === 1
          oneExp x = myPow x 1 === x
          general x y = y > 0 ==> myPow x y === x ^ y

unit_isPrime = do
    assertBool "isPrime 17" $ isPrime 17
    assertBool "isPrime 23" $ isPrime 23
    assertBool "isPrime 2" $ isPrime 2
    assertBool "not isPrime 9" $ not $ isPrime 9
    assertBool "not isPrime 8" $ not $ isPrime 8
    assertBool "not isPrime 121" $ not $ isPrime 121
    assertBool "not isPrime 34" $ not $ isPrime 34

unit_shapeArea = do
    shapeArea [(0, 0), (0, 1), (1, 1), (1, 0)] @?=~ 1
    shapeArea [(0, 0), (0, 1), (1, 1)] @?=~ 0.5
    shapeArea [(2, 1), (4, 5), (7, 8)] @?=~ 3
    shapeArea [(3, 4), (5, 11), (12, 8), (9, 5), (5, 6)] @?=~ 30

prop_shapeArea (x, y) w h =
    let
        w' = fromInteger w
        h' = fromInteger h
        x' = fromInteger x
        y' = fromInteger y
        x'' = x' + w'
        y'' = y' + h'
        case1 = shapeArea [(x', y'), (x', y''), (x'', y''), (x'', y')] `nearlyEqual'` abs h' * abs w'
        case2 = shapeArea [(x', y'), (x', y''), (x'', y'')] `nearlyEqual'` abs h' * abs w' / 2
        case3 = shapeArea [(x', y'), (x'', y''), (x'', y')] `nearlyEqual'` abs h' * abs w' / 2
    in
        w /= 0 && h /= 0 ==> case1 .&&. case2 .&&. case3

unit_triangleKind =
    do
        triangleKind 3.0 7.5 4.0 @?= (-1)
        triangleKind 5.0 3.0 4.0 @?= 2 -- это прямоугольный треугольник
        triangleKind 4.0 6.0 8.0 @?= 0 -- это тупоугольный треугольник
        triangleKind 1.0 1.5 1.5 @?= 1 -- это остроугольный треугольник

