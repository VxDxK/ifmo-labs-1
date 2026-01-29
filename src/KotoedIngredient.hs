-- Этот код нужен для запуска тестов в Котоеде, не трогайте его, пожалуйста
{-# LANGUAGE CPP #-}
{-# LANGUAGE DeriveDataTypeable #-}
{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE FlexibleContexts #-}
{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE NoMonomorphismRestriction #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE PatternSynonyms #-}
module KotoedIngredient (kotoedIngredient, composite) where

import Numeric (showFFloat)
import Data.Functor
import Data.Function
import Control.Applicative
import Control.Arrow (first)
import Control.Monad.IO.Class (liftIO)
import Data.List (intercalate, isInfixOf)
import Data.String(fromString, IsString(..))
import Data.Maybe (fromMaybe)
import Data.Monoid (Monoid(..), Endo(..), Sum(..))
import Data.Proxy (Proxy(..))
import Data.Typeable (Typeable)
import GHC.Generics (Generic)
import Control.Monad (forM_)
import System.Directory (createDirectoryIfMissing, canonicalizePath)
import System.FilePath (takeDirectory)

import qualified Control.Concurrent.STM as STM
import qualified Control.Monad.State as State
import qualified Control.Monad.Reader as Reader
import qualified Data.Functor.Compose as Functor
import qualified Data.IntMap as IntMap
import qualified Test.Tasty as Tasty
import qualified Test.Tasty.Providers as Tasty
import qualified Test.Tasty.Options as Tasty
import qualified Test.Tasty.Runners as Tasty
import qualified Test.Tasty.Ingredients.ConsoleReporter as Tasty

import qualified Data.Aeson as J
import Data.Aeson((.:), (.=))

import Util(NotImplementedYet(..))
import Test.Tasty.Ingredients (composeReporters)

import qualified System.Console.Pretty as Pretty

data Chunk = Chunk { contents :: String, fgColor :: Pretty.Color, bgColor :: Pretty.Color, style :: Pretty.Style }
chunk :: String -> Chunk
chunk s = Chunk s Pretty.Default Pretty.Default Pretty.Normal

instance IsString Chunk where
  fromString = chunk

codify (Chunk c fg bg s) =
  c & (Pretty.color fg) & (Pretty.bgColor bg) & (Pretty.style s)

data KotoedRunnerStatus =  ABORTED | SUCCESSFUL | NOT_IMPLEMENTED | FAILED
    deriving (Show, Eq, Bounded, Enum, Generic)
data KotoedRunnerTestFailure = KotoedRunnerTestFailure {
    nestedException :: Maybe String,
    errorMessage :: Maybe String
} deriving (Show, Eq, Generic)
data KotoedRunnerTestResult = KotoedRunnerTestResult {
    status :: KotoedRunnerStatus,
    failure :: Maybe KotoedRunnerTestFailure
} deriving (Show, Eq, Generic)
data KotoedRunnerTestMethodRun = KotoedRunnerTestMethodRun {
    tags :: [String],
    results :: [KotoedRunnerTestResult],
    methodName :: String,
    packageName :: String
} deriving (Show, Eq, Generic)
newtype KotoedRunnerTestRun = KotoedRunnerTestRun {
    krtData :: [KotoedRunnerTestMethodRun]
} deriving (Show, Eq, Semigroup, Monoid)

class Chunky a where
    toChunks :: a -> [Chunk]
    listToChunks :: [a] -> [Chunk]
    listToChunks s = concatMap toChunks s

instance Chunky Char where
    toChunks c = listToChunks [c]
    listToChunks s = [fromString s]

instance Chunky Chunk where
    toChunks s = [s]
    listToChunks s = s

instance (Chunky a) => Chunky [a] where
    toChunks s = listToChunks s

mix c1 c2 = toChunks c1 ++ toChunks c2

simpleChunk :: (Show a) => a -> [Chunk]
simpleChunk s = toChunks $ show s

printR :: (Chunky a) => a -> IO ()
printR x = putStrLn $ concat (codify <$> toChunks x)

newLine = chunk "\n"

instance Chunky KotoedRunnerStatus where
    toChunks s =
      let color SUCCESSFUL c = c { fgColor = Pretty.Green }
          color NOT_IMPLEMENTED c = c { style = Pretty.Faint }
          color _ c = c { fgColor = Pretty.Red, style = Pretty.Bold }
          rep = show s in
      color s <$> toChunks rep

instance Chunky KotoedRunnerTestFailure where
    toChunks (KotoedRunnerTestFailure nestedException errorMessage) =
      let combined =
            case (nestedException, errorMessage) of
                 (Just ex,         Just e) -> ex `mix` newLine `mix` e
                 (Just ex,         _)      -> toChunks ex
                 (_      ,         Just e) -> toChunks e
                 _                         -> []
      in (\c -> c { fgColor = Pretty.Red }) <$> combined
instance Chunky KotoedRunnerTestResult where
    toChunks (KotoedRunnerTestResult status failure) = status `mix` failureTail failure
      where failureTail (Just e) = newLine `mix` e
            failureTail Nothing = []

instance Chunky KotoedRunnerTestMethodRun where
    toChunks (KotoedRunnerTestMethodRun tags results methodName packageName) =
      packageName `mix` chunk "." `mix` methodName `mix` chunk ": " `mix` results

instance Chunky KotoedRunnerTestRun where
    toChunks (KotoedRunnerTestRun runs) = intercalate ["\n"] $ toChunks <$> runs

instance J.ToJSON KotoedRunnerTestFailure
instance J.FromJSON KotoedRunnerTestFailure

instance J.ToJSON KotoedRunnerStatus
instance J.FromJSON KotoedRunnerStatus

instance J.ToJSON KotoedRunnerTestResult
instance J.FromJSON KotoedRunnerTestResult

instance J.ToJSON KotoedRunnerTestMethodRun
instance J.FromJSON KotoedRunnerTestMethodRun

instance J.ToJSON KotoedRunnerTestRun where
    toJSON (KotoedRunnerTestRun data') =
        J.object ["data" .= J.toJSON data']
    toEncoding (KotoedRunnerTestRun data') =
        J.pairs ("data" .= data')

instance J.FromJSON KotoedRunnerTestRun where
    parseJSON = J.withObject "KotoedRunnerTestRun" $ \ v -> KotoedRunnerTestRun <$> v .: "data"

type Summary = KotoedRunnerTestRun
pattern Summary a = KotoedRunnerTestRun a

kotoedIngredient :: Tasty.Ingredient
kotoedIngredient = Tasty.TestReporter [] runner
 where
  runner options testTree = do
    let path = "results.json"
    return $ \statusMap ->
      let
        timeDigits = 3
        showTime time = showFFloat (Just timeDigits) time ""

        runTest :: (Tasty.IsTest t)
                => Tasty.OptionSet
                -> Tasty.TestName
                -> t
                -> Tasty.Traversal (Functor.Compose (Reader.ReaderT [String] (State.StateT IntMap.Key IO)) (Const Summary))
        runTest _ testName _ = Tasty.Traversal $ Functor.Compose $ do
          i <- State.get
          groupNames <- Reader.ask

          summary <- liftIO $ STM.atomically $ do
            status <- STM.readTVar $
              fromMaybe (error "Attempted to lookup test by index outside bounds") $
                IntMap.lookup i statusMap
            let package = intercalate "." $ tail $ reverse groupNames
            let mkResult r = Summary [KotoedRunnerTestMethodRun [] [r] testName package]
                mkSuccess = mkResult $ KotoedRunnerTestResult SUCCESSFUL Nothing
                mkNotImpl = mkResult $ KotoedRunnerTestResult NOT_IMPLEMENTED Nothing
                mkFailure ex result = mkResult $ KotoedRunnerTestResult FAILED err
                  where err = Just $ KotoedRunnerTestFailure ex (Just $ Tasty.resultDescription result)
                processResult result | Tasty.resultSuccessful result = mkSuccess
                processResult result =
                  case resultException result of
                        Just e  | show e == show NotImplementedYet -> mkNotImpl
                                | "No instance nor default method for class" `isInfixOf` show e -> mkNotImpl
                                | otherwise -> mkFailure (Just $ show e) result
                        Nothing | "Exception: '#FUNCTION_NOT_IMPLEMENTED#'" `isInfixOf` Tasty.resultDescription result -> mkNotImpl
                                | "Exception: #FUNCTION_NOT_IMPLEMENTED#" `isInfixOf` Tasty.resultDescription result -> mkNotImpl
                                | "No instance nor default method for class" `isInfixOf` Tasty.resultDescription result -> mkNotImpl
                                | otherwise -> mkFailure Nothing result
            case status of
              -- If the test is done, generate JSON for it
              Tasty.Done result -> do
                return $ processResult result
              -- Otherwise the test has either not been started or is currently
              -- executing
              _ -> STM.retry
          liftIO $ printR summary
          Const summary <$ State.modify (+ 1)

        runGroup _options groupName children = Tasty.Traversal $ Functor.Compose $ do
          Reader.local (groupName :) $ Functor.getCompose $ Tasty.getTraversal children

      in do
        (Const summary, tests) <-
          flip State.runStateT 0 $ flip Reader.runReaderT [] $ Functor.getCompose $ Tasty.getTraversal $
           Tasty.foldTestTree
             Tasty.trivialFold { Tasty.foldSingle = runTest, Tasty.foldGroup = runGroup }
             options
             testTree

        return $ \elapsedTime -> do
          createPathDirIfMissing path
          J.encodeFile path summary
          -- return $ notElem FAILED [status result | run <- krtData summary, result <- results run]
          -- unfortunately, kotoed expects all processes to return 0, otherwise it's considered a build failure
          return True

  resultException r =
    case Tasty.resultOutcome r of
         Tasty.Failure (Tasty.TestThrewException e) -> Just e
         _ -> Nothing

  resultTimedOut r =
    case Tasty.resultOutcome r of
         Tasty.Failure (Tasty.TestTimedOut _) -> True
         _ -> False

  createPathDirIfMissing path = do
        canonical <- canonicalizePath path
        createDirectoryIfMissing True (takeDirectory canonical)

composite = kotoedIngredient
