package laminterp

import utest._

object MainTests extends TestSuite {
  val tests = Tests {
    "complex" - {
      parse.Parser.parse(
        "app app (app (lam f lam y lam x (app (app f y) x)) (lam x lam y x)) 3 4"
      ) match {
        case Right(expr) => evaluate.Evaluator.eval(expr) ==> 3
        case Left(error) => throw new Exception(error)
      }
    }
  }
}
