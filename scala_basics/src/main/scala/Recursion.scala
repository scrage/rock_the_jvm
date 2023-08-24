import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else n * factorial(n-1)

  // the above recursive function eats up stack frame - stack overflow happens eventually

  def anotherFactorial(n: Int): BigInt = {
    def factHelper(x: Int, accumulator: BigInt): BigInt =
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator)

    factHelper(n, 1)
  }

  // tail recursive calls replace the stack frames, their intermediate results are stored in accumulators
  //println(anotherFactorial(50000))

  // WHEN YOU NEED LOOPS, USE TAIL RECURSION!

  // any recursive functions can be turned into tail recursive by either using helper functions with the accumulator
  // (as many accumulators as many recursive calls needed in the code paths)
  // or having the accumulator parameter with a default value so that it must not be defined at function call

  // 1. concatenate a string n times
  // 2. isPrime function
  // 3. fibonacci function

  def concatenateTailRec(aString: String, n: Int, acc: String = ""): String =
    if (n <= 0) acc
    else concatenateTailRec(aString, n-1, aString + acc)

  println(concatenateTailRec("hello ", 5))

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeTailRec(t: Int, isStillPrime: Boolean): Boolean =
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailRec(t - 1, n % t != 0 && isStillPrime)

    isPrimeTailRec(n / 2, true)
  }

  println(isPrime(2003))
  println(isPrime(629))

  def fibonacci(n: Int): Int = {
    @tailrec
    def fibonacciTailRec(i: Int, acc1: Int = 1, acc2: Int = 1): Int =
      if (i >= n) acc1
      else fibonacciTailRec(i + 1, acc1 + acc2, acc1)

    if (n <= 2) 1
    else fibonacciTailRec(2)
  }

  println(fibonacci(8))
}
