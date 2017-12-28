package laminterp
package evaluate


trait TYPE
case object UNIT extends TYPE
case class INT(value: Int) extends TYPE
case class BOOL(value: Boolean) extends TYPE
case class LAMBDA[-T <: TYPE, +R <: TYPE](value: Function1[T, R], name: String = "") extends TYPE

object Evaluator {

  /*
   * built-in functions
   */
  val ADD = LAMBDA((a: INT) => LAMBDA((b: INT) => INT(a.value + b.value)), "add")
  val GT = LAMBDA((a: INT) => LAMBDA((b: INT) => BOOL(a.value > b.value)), "gt")
  val IF = 
    LAMBDA((cond: BOOL) => 
      LAMBDA { (truthy: LAMBDA[TYPE, TYPE]) => 
        LAMBDA { (falsy: LAMBDA[TYPE, TYPE]) => 
          LAMBDA { (_: UNIT.type) => 
            if(cond.value) truthy.value(UNIT)
            else falsy.value(UNIT)
          }
        }
      }
    , "if")

  val builtin = Map(
    "gt" -> GT,
    "if" -> IF,
    "add" -> ADD
  )

  def eval(expr: ast.Expr): TYPE =
    eval(expr, builtin)

  def eval(expr: ast.Expr, stack: Map[String, TYPE]): TYPE = expr match {
    case ast.ENumber(v) => INT(v)
    case ast.EBool(b) => BOOL(b)
    case ast.Paren(e) => eval(e, stack)
    case ast.Application(fst, snd) =>
      (fst match {
        case ast.Identifier(id) => stack.get(id)
        case ast.Lambda(_, _) => Some(eval(expr, stack))
        case _ => None
      }) match {
        case Some(lambda: LAMBDA[TYPE, TYPE]) =>
          val second: TYPE = eval(snd, stack)
          println(second)
          lambda.value(second)
        case _ => throw new IllegalArgumentException("""
          | expeceted: app {lam} expr
          | - Application must take a lambda as first parameter.""".stripMargin)
      }
  }
}