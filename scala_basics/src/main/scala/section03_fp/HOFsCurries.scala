package section03_fp

object HOFsCurries extends App {

  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null
  // the way of reading the function definition above is:
  // 1. look at the return type of the lambda, the very right side of the arrow
  // 2. the parameters are on the left side of the arrow:
  // 3. within parentheses the first input parameter is of type Int,
  // 4. the second input parameter is another lambda function: (String, (Int => Boolean)) => Int

  // a function that applies a function n times over a value x:
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x))
  def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 5))
  // prints 15

  // a better way to write the above HOF is to have the function to return a function that returns type of (Int -> Int)
  // so that instead of applying f n-times to the value x, we save that for later to reuse it however many times we want
  // ntb(f, n) = x => f(f(f...(x)))
  // increment10 = ntb(plusOne, 10) = x => plusOne(plusOne...(x))
  // val y = increment10(5)

  // this is a subtle distinction on a first glance, but is important
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1) (f(x))

  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(5))
  // prints 15

  // curried functions
  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))           // 13
  println(superAdder(3)(10))  // 13

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))  // 3.14
  println(preciseFormat(Math.PI))   // 3.14159265

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) =
    x => y => f(x, y)

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(17)) // prints: 21

  val simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4, 17)) // prints: 21

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)
  println(composed(4))  // prints: 14 -> (4 * 3) + 2
  println(ordered(4))   // prints: 18 -> (4 + 2) * 3

}
