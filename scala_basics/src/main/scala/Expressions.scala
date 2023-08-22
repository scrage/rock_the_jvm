object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4) // math expression
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x) // relational expression evaluating to a Boolean
  // == != > >= < <=

  println(!(1 == x)) // logical negation, unary operator
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ... these are all side-effects!
  println(aVariable)

  // Instructions (side-effects) vs Expressions (expresses/computes a value of a type)

  // IF expression
  val aCondition = true
  val aConditioedValue = if(aCondition) 5 else 3 // the IF expression gives back a value
  println(aConditioedValue)
  println(if(aCondition) 5 else 3)

  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  // NEVER USE IMPERATIVE CODE IN SCALA! AVOID LOOPS!
  // EVERYTHING in Scala is an expression!

  val aWeirdValue = (aVariable = 3) // Unit === void
  println(aWeirdValue)

  // loops also return Unit
  // side-effects: println(), whiles, reassigning

  // Code blocks

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  } // the value of a code block is always the value of the last expression

  // 1. the difference between "hello word" and println("hello world") is that the former is of type String, the latter is of Unit
  // 2. someValue will have the value true, someOtherValue will have the value 42
  
  val someValue = {
    2 < 3
  }

  val someOtherValue = {
    if(someValue) 239 else 986
    42
  }
}
