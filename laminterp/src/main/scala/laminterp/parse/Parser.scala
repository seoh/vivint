package laminterp
package parse

import fastparse.all._
import ast._

object Parser {
  val letter = CharIn('a'to'z')
  val digit = CharIn('0'to'9')

  val identifier: P[Identifier] = P(letter ~ (letter | digit).rep).! map (name => Identifier(name))
  val number: P[Literal] = P(digit.rep(1) ).! map (n => ENumber(n.toInt))
  val boolean: P[Literal] = P(("true" | "false")).! map (b => EBool(b.toBoolean))
  val literal: P[Literal] = P(boolean | number)

  lazy val paren: P[Paren] = P( ("(" ~ expr ~ ")") ) map Paren
  lazy val lambda: P[Lambda] = P( ("lam" ~ " " ~ identifier ~ " " ~ expr) ) map {
    case (i, e) => Lambda(i, e)
  }
  lazy val app: P[Application] = P( ("app" ~ " " ~ expr ~ " " ~ expr) ) map {
    case (e1, e2) => Application(e1, e2)
  }
  lazy val expr: P[Expr] = P(paren | lambda | app | literal | identifier)
  
  def parse(line: String): Either[String, ast.Expr] = expr.parse(line) match {
    case Parsed.Success(expr, _) => Right(expr)
    case f => Left(f.toString)
  }
}