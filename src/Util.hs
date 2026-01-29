module Util where

import Data.Typeable
import Control.Exception.Safe
import Test.Tasty.HUnit (Assertion, assertBool, HasCallStack)

data NotImplementedYet = NotImplementedYet deriving (Typeable)
instance Exception NotImplementedYet
instance Show NotImplementedYet where
    show _ = "#FUNCTION_NOT_IMPLEMENTED#"

notImplementedYet :: a
notImplementedYet = impureThrow NotImplementedYet

assertFalse :: HasCallStack => String -> Bool -> Assertion
assertFalse prefix value = assertBool prefix (not value)
