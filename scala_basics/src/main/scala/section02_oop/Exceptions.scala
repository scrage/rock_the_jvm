package section02_oop

// throwing and catching exceptions
object Exceptions extends App {

  val x: String = null

  // 1. throwing an exception - it is an expression in scala
  // throw new NullPointerException()
  val aWeirdValue = throw new NullPointerException()
  // the above will not actually hold a value, but can be assigned to things

  // this is also valid:
  val anotherWeirdValue: String = throw new NullPointerException
  // throwable classes extend the Throwable class
  // Exception and Error are the major Throwable subtypes - both will crash the JVM, but
  // Exceptions denote that something went wrong in the program, Errors denote that something went wrong in the system

  // 2. catching exceptions
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  try {
    // code that might fail
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught a Runtime exception.")
  } finally {
    // code that will get executed no matter what, just like in other languages
    println("finally")
  }

  // a try-catch-finally block is also a scala expression, and it returns an AnyVal if the compiler cannot determine a
  // single common return type in each blocks
  val potentialFail = try {
    getInt(true)
  } catch {
    case e: RuntimeException => println("caught a Runtime exception.")
  } finally {
    println("finally")
  }

  val potentialIntFail: Int = try {
    getInt(true)
  } catch {
    case e: RuntimeException => 35
  } finally {
    // the finally block is optional and does not influence the return type of this expression - use it for side-effects!
    println("finally")
  }

  println(potentialIntFail)
  // 35

  // 3. defining own Exceptions
  class MyException extends Exception
  val exception = new MyException

  throw exception

}
