module Part5.Tasks where

import Util(notImplementedYet)

-- Реализуйте левую свёртку
myFoldl :: (b -> a -> b) -> b -> [a] -> b
myFoldl _ acc [] = acc
myFoldl f acc (x:xs) = myFoldl f (f acc x) xs

-- Реализуйте правую свёртку
myFoldr :: (a -> b -> b) -> b -> [a] -> b
myFoldr _ acc [] = acc
myFoldr f acc (x:xs) = f x (myFoldr f acc xs)

-- Используя реализации свёрток выше, реализуйте все остальные функции в данном файле

myMap :: (a -> b) -> [a] -> [b]
myMap f a = myFoldr (\x acc -> f x : acc) [] a

myConcatMap :: (a -> [b]) -> [a] -> [b]
myConcatMap f a = myFoldr (\x acc -> f x ++ acc) [] a

myConcat :: [[a]] -> [a]
myConcat a = myFoldr (++) [] a

myReverse :: [a] -> [a]
myReverse = myFoldl (\acc x -> x : acc) []

myFilter :: (a -> Bool) -> [a] -> [a]
myFilter p a = myFoldr (\x acc -> if p x then x : acc else acc) [] a

myPartition :: (a -> Bool) -> [a] -> ([a], [a])
myPartition p = myFoldr 
    (\x (trues, falses) -> 
        if p x 
        then (x : trues, falses) 
        else (trues, x : falses))
    ([], [])