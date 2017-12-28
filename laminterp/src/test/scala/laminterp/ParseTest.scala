package laminterp

import utest._
import parse.Parser
import ast._

object ParseTests extends TestSuite {
  private def success(line: String, expr: Expr): Unit =
    Parser.parse(line) match {
      case Right(e) => require(e == expr, s"""
        | ${e.toString}
        | \tis not matched with
        | ${expr.toString}
        """.stripMargin
      )
    }

  val tests = Tests {
    "id - case1" - success(
      "app (lam x x) 2",
      Application(Paren(Lambda(Identifier("x"), Identifier("x"))), ENumber(2))
    )

    "id - case2" - success(
      "app lam x (x) 2",
      Application(Lambda(Identifier("x"), Paren(Identifier("x"))), ENumber(2))
    )

    "add 1 3" - success(
      "app app add 1 3",
      Application(Application(Identifier("add"), ENumber(1)), ENumber(3))
    )

    "if and gt" - success(
      "app app app if (app app gt 3 1) 10 5",
      Application(
        Application(
          Application(
            Identifier("if"),
            Paren(
              Application(
                Application(
                  Identifier("gt"),
                  ENumber(3)
                ),
                ENumber(1)
              )
            )
          ),
          ENumber(10)
        ),
        ENumber(5)
      )
    )

  //   "complex" - success(
  //     "app app (app (lam f lam y lam x (app (app f y) x)) (lam x lam y x)) 3 4",
  //     Application(
  //       Application(
  //         Paren(Application(
  //           Paren(Lambda(
  //             Identifier("f"),
  //             Lambda(
  //               Identifier("y"),
  //               Lambda(
  //                 Identifier("x"),
  //                 Paren(Application(
  //                   Paren(Application(
  //                     Identifier("f"),
  //                     Identifier("y")
  //                   )),
  //                   Identifier("x")
  //                 ))
  //               )
  //             )
  //           )),
  //           Paren(Lambda(
  //             Identifier("x"),
  //             Lambda(
  //               Identifier("y"),
  //               Identifier("x")
  //             )
  //           ))
  //         )),
  //         ENumber(3)
  //       ),
  //       ENumber(4)
  //     )
  //   )
  }
}