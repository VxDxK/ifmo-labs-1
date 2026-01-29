{-# LANGUAGE FlexibleInstances #-}
module Part6.Tasks where

import Util (notImplementedYet)
import qualified Data.Map as Map

-- Разреженное представление матрицы. Все элементы, которых нет в sparseMatrixElements, считаются нулями
data SparseMatrix a = SparseMatrix {
                                sparseMatrixWidth :: Int,
                                sparseMatrixHeight :: Int,
                                sparseMatrixElements :: Map.Map (Int, Int) a
                         } deriving (Show, Eq)

-- Определите класс типов "Матрица" с необходимыми (как вам кажется) операциями,
-- которые нужны, чтобы реализовать функции, представленные ниже
class Matrix m where
    getSize :: m -> (Int, Int)
    getElem :: m -> Int -> Int -> Int
    createMatrix :: Int -> Int -> (Int -> Int -> Int) -> m

-- Определите экземпляры данного класса для:
--  * числа (считается матрицей 1x1)
--  * списка списков чисел
--  * типа SparseMatrix, представленного выше
instance Matrix Int where
    getSize _ = (1, 1)
    getElem m 0 0 = m
    getElem _ _ _ = error "Index out of bounds"
    createMatrix w h f
        | w == 1 && h == 1 = f 0 0
        | otherwise = error "Int can only be 1x1 matrix"

instance Matrix [[Int]] where
    getSize matrix = (length (head matrix), length matrix)
    getElem matrix x y = (matrix !! y) !! x
    createMatrix w h f = 
        [ [ f x y | x <- [0..w-1] ] | y <- [0..h-1] ]

instance Matrix (SparseMatrix Int) where
    getSize (SparseMatrix w h _) = (w, h)
    getElem (SparseMatrix _ _ elements) x y =
        case Map.lookup (x, y) elements of
            Just val -> val
            Nothing -> 0
    createMatrix w h f =
        let allCoords = [ ((x, y), f x y) | y <- [0..h-1], x <- [0..w-1] ]
            nonZero = filter (\(_, val) -> val /= 0) allCoords
        in SparseMatrix w h (Map.fromList nonZero)

-- Реализуйте следующие функции
-- Единичная матрица
eye :: Matrix m => Int -> m
eye n = createMatrix n n (\x y -> if x == y then 1 else 0)

-- Матрица, заполненная нулями
zero :: Matrix m => Int -> Int -> m
zero w h = createMatrix w h (\_ _ -> 0)

-- Перемножение матриц
multiplyMatrix :: Matrix m => m -> m -> m
multiplyMatrix a b =
    let (w1, h1) = getSize a
        (w2, h2) = getSize b
    in if w1 /= h2
        then error "Incompatible matrix dimensions for multiplication"
        else createMatrix w2 h1 $ \i j ->
            sum [ getElem a k j * getElem b i k | k <- [0..w1-1] ]

-- Определитель матрицы
determinant :: Matrix m => m -> Int
determinant m =
    let (w, h) = getSize m
    in if w /= h
        then error "Determinant only defined for square matrices"
        else determinant' (toListMatrix m)
    where
        toListMatrix m =
            let (w, h) = getSize m
            in [ [ getElem m x y | x <- [0..w-1] ] | y <- [0..h-1] ]
        
        determinant' [] = 1
        determinant' [[x]] = x
        determinant' matrix =
            let n = length matrix
                firstRow = head matrix
                minors = [ removeRowCol matrix 0 i | i <- [0..n-1] ]
                cofactors = [ (-1)^i * (firstRow !! i) * determinant' (minors !! i) 
                            | i <- [0..n-1] ]
            in sum cofactors
        
        removeRowCol matrix row col =
            [ [ row' !! x | x <- [0..n-1], x /= col ] 
            | (y, row') <- zip [0..] matrix, y /= row ]
            where n = length matrix