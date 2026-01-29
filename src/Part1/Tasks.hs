module Part1.Tasks where

import Data.List
import Util (notImplementedYet)

-- синус числа (формула Тейлора)
mySin :: Double -> Double
mySin x = sign * sinTaylor xReduced
  where
    twoPi = 2 * pi
    xWrapped = x - twoPi * fromIntegral (round (x / twoPi))
    sign = if xWrapped < 0 then -1 else 1
    xAbs = abs xWrapped
    xReduced = if xAbs > pi / 2 then pi - xAbs else xAbs

    sinTaylor :: Double -> Double
    sinTaylor x' = helper 0 x'
      where
        xSquared = x' * x'
        helper n term
          | abs term < 1e-10 = term
          | otherwise = term + helper (n + 1) (- term * xSquared / ((2 * n + 2) * (2 * n + 3)))

-- косинус числа (формула Тейлора)
myCos :: Double -> Double
myCos x = sign * cosTaylor xReduced
  where
    twoPi = 2 * pi
    xWrapped = x - twoPi * fromIntegral (round (x / twoPi))
    xAbs = abs xWrapped

    (sign, xReduced)
      | xAbs <= pi / 2 = (1, xAbs)
      | xAbs <= pi = (-1, pi - xAbs)
      | xAbs <= 3 * pi / 2 = (-1, xAbs - pi)
      | otherwise = (1, twoPi - xAbs)

    cosTaylor :: Double -> Double
    cosTaylor x' = helper 0 1.0
      where
        xSquared = x' * x'
        helper n term
          | abs term < 1e-10 = term
          | otherwise = term + helper (n + 1) (- term * xSquared / ((2 * n + 1) * (2 * n + 2)))

-- наибольший общий делитель двух чисел
myGCD :: Integer -> Integer -> Integer
myGCD a 0 = abs a
myGCD a b = myGCD b (a `mod` b)

-- является ли дата корректной с учётом количества дней в месяце и
-- вискокосных годов?
isDateCorrect :: Integer -> Integer -> Integer -> Bool
isDateCorrect day month year
  | month < 1 || month > 12 || day < 1 || year < 0 = False
  | otherwise = day <= daysInMonth month year
  where
    daysInMonth 2 year
      | isLeapYear year = 29
      | otherwise = 28
    daysInMonth month _
      | elem month [4, 6, 9, 11] = 30
      | otherwise = 31

    isLeapYear year
      | year `mod` 400 == 0 = True
      | year `mod` 100 == 0 = False
      | year `mod` 4 == 0 = True
      | otherwise = False

-- возведение числа в степень, duh
-- готовые функции и плавающую арифметику использовать нельзя
myPow :: Integer -> Integer -> Integer
myPow x 0 = 1
myPow x n = x * myPow x (n - 1)

-- является ли данное число простым?
isPrime :: Integer -> Bool
isPrime n
  | n == 2 = True
  | n < 2 || n `mod` 2 == 0 = False
  | otherwise = checkDivisors 3
  where
    limit = floor (sqrt (fromIntegral n)) + 1

    checkDivisors d
      | d > limit = True
      | n `mod` d == 0 = False
      | otherwise = checkDivisors (d + 2)

type Point2D = (Double, Double)

-- рассчитайте площадь многоугольника по формуле Гаусса
-- многоугольник задан списком координат
shapeArea :: [Point2D] -> Double
shapeArea points =
  abs (sum1 - sum2) / 2
  where
    points' = points ++ [head points]
    sum1 = sum [x1 * y2 | ((x1, y1), (x2, y2)) <- zip points' (tail points')]
    sum2 = sum [y1 * x2 | ((x1, y1), (x2, y2)) <- zip points' (tail points')]

-- треугольник задан длиной трёх своих сторон.
-- функция должна вернуть
--  0, если он тупоугольный
--  1, если он остроугольный
--  2, если он прямоугольный
--  -1, если это не треугольник
triangleKind :: Double -> Double -> Double -> Integer
triangleKind a b c
  | isTriangle == False = -1
  | a' > b' + c' = 0
  | a' < b' + c' = 1
  | otherwise = 2
  where
    isTriangle = a > 0 && b > 0 && c > 0 && a + b > c && b + c > a && a + c > b
    [a', b', c'] = reverse (sort [a ^ 2, b ^ 2, c ^ 2])
