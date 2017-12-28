package laminterp

import utest._
import parse.Parser
import evaluate._

object EvalTests extends TestSuite {

  def eval(line: String): Any = {
    println(Parser.parse(line))
    Parser.parse(line) match {
      case Left(e) => throw new Exception("Parse Error: " + e)
      case Right(exp) => evaluate.Evaluator.eval(exp)
    }
  }

  val tests = Tests {
    "literal returns itself - number" - assert(eval("1") == INT(1))
    "literal returns itself - boolean" - assert(eval("false") == BOOL(false))

    "parenthes returns first-and-only child - number" - assert(eval("(1)") == INT(1))
    "parenthes returns first-and-only child - boolean" - assert(eval("(true)") == BOOL(true))

    "lambda returns function with name" - assert(eval("lam x x") match {
      case LAMBDA(fn, name) => name == "x"
      case _ => false
    })


    // "id - case2" - success(
    //   "app lam x (x) 2",
    //   Application(Lambda(Identifier("x"), Paren(Identifier("x"))), ENumber(2))
    // )

    // "add 1 3" - success(
    //   "app app add 1 3",
    //   Application(Application(Identifier("add"), ENumber(1)), ENumber(3))
    // )

    // "if and gt" - success(
    //   "app app app if (app app gt 3 1) 10 5",
    //   Application(
    //     Application(
    //       Application(
    //         Identifier("if"),
    //         Paren(
    //           Application(
    //             Application(
    //               Identifier("gt"),
    //               ENumber(3)
    //             ),
    //             ENumber(1)
    //           )
    //         )
    //       ),
    //       ENumber(10)
    //     ),
    //     ENumber(5)
    //   )
    // )
  }
}