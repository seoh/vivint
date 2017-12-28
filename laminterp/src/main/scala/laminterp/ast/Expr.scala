package laminterp
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

trait Literal extends Expr
case class ENumber(value: Int) extends Literal
case class EBool(value: Boolean) extends Literal
case class Paren(expr: Expr) extends Expr

/**
  `lam x y` means `(x) => y`
  */
case class Lambda(iden: Identifier, expr: Expr) extends Expr

/**
  `app x y` means `x(y)`

  - built-in functions
    - add: `app (app add 1) 2` == 3
    - gt: `app (app gt 1) 2` == false
    - if: `app app app if false 2 3` == 3
  */
case class Application(fst: Expr, snd: Expr) extends Expr
case class Identifier(name: String) extends Expr
