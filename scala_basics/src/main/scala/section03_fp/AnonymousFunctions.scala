package section03_fp

object AnonymousFunctions extends App {

  // a lambda is a syntactic sugar in Scala that instantiates a Function<X>[...] class under the hood
  // everything on the right side of the = is a lambda expression in Scala, and an instance object of Function1
  val doubler = (x: Int) => x * 2

  // another short-hand notation omits the type definition inside the lambda when
  // the value declaration has it, letting the compiler to match all types within the lambda via type inference
  val doubler2: Int => Int = x => x * 2

  // when having multiple (>1) parameters to the lambda, the types must be within parentheses
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // a lambda without input parameters uses a pair of empty parentheses
  val justDoSomething = () => 3
  val anotherSimpleLambda: () => Int = () => 3

  // even though methods without parameters can be called without a parentheses pair in Scala, it's not the case with lambdas!
  println(justDoSomething)
  // prints: section03_fp.AnonymousFunctions$$$Lambda$20/0x000000080102e5b8@30c7da1e
  println(justDoSomething())
  // prints: 3

  // curly braces with lambdas
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // more syntactic sugar
  val niceIncrementer: Int => Int = _ + 1   // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _  // equivalent to (a, b) => a + b
  // the above short-hand notations require the explicit type definitions for the compiler to know what _ represent(s)
  // also, each underscore stands for an individual element, so we can't use _ multiple times to refer to the same thing

  // a curried lambda function:
  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))
  // prints: 7
}
