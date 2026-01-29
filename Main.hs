module Main where

-- A simple main program. The program reads the contents of the
-- given input file, checks whether the content is a number, and
-- then ouputs the results of apply funA and funB to this number.


import System.Environment (getArgs)
import System.Exit
import System.IO

main = do
    args <- getArgs
    print args
