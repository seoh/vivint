package laminterp

import utest._
import parse.Parser._
import fastparse.all.Parsed

object TermTests extends TestSuite {
  def success(p: Parsed[_]): Unit =
    p match { case s: Parsed.Success[_] => }
  
  def failure(p: Parsed[_]): Unit =
    p match { case f: Parsed.Failure => }


  val tests = Tests {
    "digit.success" - success(digit.parse("1"))
    "digit.failure" - failure(digit.parse("a"))

    "letter.success" - success(letter.parse("a"))
    "letter.failure" - failure(letter.parse("1"))

    "boolean.success" - {
      success(boolean.parse("true"))
      success(boolean.parse("false"))
    }
    "boolean.failure" - {
      failure(boolean.parse("yes"))
      failure(boolean.parse("no"))      
    }
    
    "identifier.success" - success(identifier.parse("a123"))
    "identifier.failure" - failure(identifier.parse("1a"))

    "application.success" - success(expr.parse("app 1 1"))
    "lambda.success" - success(expr.parse("lam a 1"))

    "paren.number" - success(expr.parse("(1)"))
    "paren.bool" - success(expr.parse("(false)"))

    "paren.bool" - success(paren.parse("(true)"))
    "paren.bool" - success(paren.parse("(false)"))

    "paren.success" - {
      success(expr.parse("(lam a 1)"))
      success(expr.parse("lam a (1)"))
    }

  }
}

