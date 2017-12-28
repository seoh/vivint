package hm
package ast

/**
 expr = "(" expr ")"
      | "lam" iden expr
      | "app" expr expr
      | literal
      | iden

 iden    = letter, { letter | digit }
 literal = number | bool
 number  = digit, { digit }
 bool    = "true" | "false"

 letter = "A" | ... | "Z"
 digit  = "0" | ... | "9"
 */
trait Expr
case class Paren(expr: Expr) extends Expr
case class Lambda(iden: Identifier, expr: Expr) extends Expr
case class Application(fst: Expr, snd: Expr) extends Expr
case class Identifier(name: String) extends Expr

trait Literal extends Expr
case class ENumber(value: Int) extends Literal
case class EBool(value: Boolean) extends Literal
