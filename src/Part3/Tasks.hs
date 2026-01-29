module Part3.Tasks where

import Util (notImplementedYet)

-- Функция finc принимает на вход функцию f и число n и возвращает список чисел [f(n), f(n + 1), ...]
finc :: (Int -> a) -> Int -> [a]
finc f n = [f (n)] ++ finc f (n + 1)

-- Функция ff принимает на вход функцию f и элемент x и возвращает список [x, f(x), f(f(x)), f(f(f(x))) ...]
ff :: (a -> a) -> a -> [a]
ff f x = [x] ++ ff f (f x)

-- Дан список чисел. Вернуть самую часто встречающуюся *цифру* в этих числах (если таковых несколько -- вернуть любую)
mostFreq :: [Int] -> Int
mostFreq nums =
  let allDigits = getDigits nums
      counts = countDigits allDigits
   in findMaxDigit counts
  where
    getDigitsFromNumber :: Int -> [Int]
    getDigitsFromNumber n
      | n < 0 = getDigitsFromNumber (-n)
      | n < 10 = [n]
      | otherwise = getDigitsFromNumber (n `div` 10) ++ [n `mod` 10]

    getDigits :: [Int] -> [Int]
    getDigits [] = []
    getDigits (n : ns) = getDigitsFromNumber n ++ getDigits ns

    countDigits :: [Int] -> [(Int, Int)]
    countDigits digits =
      let countForDigit :: Int -> (Int, Int)
          countForDigit d = (d, countDigit d digits)

          countDigit :: Int -> [Int] -> Int
          countDigit _ [] = 0
          countDigit d (x : xs)
            | d == x = 1 + countDigit d xs
            | otherwise = countDigit d xs
       in map countForDigit [0 .. 9]

    findMaxDigit :: [(Int, Int)] -> Int
    findMaxDigit [] = 0
    findMaxDigit [(d, c)] = d
    findMaxDigit ((d1, c1) : (d2, c2) : rest)
      | c1 >= c2 = findMaxDigit ((d1, c1) : rest)
      | otherwise = findMaxDigit ((d2, c2) : rest)

-- Дан список lst. Вернуть список элементов из lst без повторений, порядок может быть произвольным.
uniq :: (Eq a) => [a] -> [a]
uniq [] = []
uniq (x : xs)
  | elem x xs = uniq xs
  | otherwise = x : uniq xs

-- Функция grokBy принимает на вход список Lst и функцию F и каждому возможному
-- значению результата применения F к элементам Lst ставит в соответствие список элементов Lst,
-- приводящих к этому результату. Результат следует представить в виде списка пар.
grokBy :: (Eq k) => (a -> k) -> [a] -> [(k, [a])]
grokBy f [] = []
grokBy f (x : xs) =
  let key = f x
      otherGroups = grokBy f xs
      (found, notFound) = findAndRemove key otherGroups
   in case found of
        Nothing -> (key, [x]) : notFound
        Just group -> (key, x : group) : notFound
  where
    findAndRemove :: (Eq k) => k -> [(k, [a])] -> (Maybe [a], [(k, [a])])
    findAndRemove key [] = (Nothing, [])
    findAndRemove key ((k, group) : rest)
      | key == k = (Just group, rest)
      | otherwise =
          let (found, others) = findAndRemove key rest
           in (found, (k, group) : others)
