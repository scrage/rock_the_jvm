package section01_basics

object MiscTopics extends App {

  // === CALL-BY-NAME VS CALL-BY-VALUE ===

  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x)
    println("by name: " + x)
  }

  calledByValue(System.nanoTime())  // prints the same time in ms twice
  calledByName(System.nanoTime())   // prints different time values each time

  // Values of parameters called by name will be evaluated each time when they are begin used,
  // and will always be evaluated first when they are being used. - Which means that they can
  // be passed around by name without necessarily needing to be evaluated EVER in a given scope.

  // === DEFAULT AND NAMED ARGUMENTS ===

  // Use default parameter values:
  // 1) In order to not pollute/spoil the function signature with accumulator parameters at their usage,
  // default parameter values should be used. They will be visually hidden for the users of the function.
  def tailRecFactorial(n: Int, acc: Int = 1): Int =
    if (n <= 1) acc
    else tailRecFactorial(n - 1, n * acc)

  println(tailRecFactorial(5))
  println(tailRecFactorial(5, 1))
  println(tailRecFactorial(5, 2))

  // 2) to assume a recurring value for a specific argument.
  def savePicture(format: String = "jpeg", width: Int = 1920, height: Int = 1080): Unit = println("saving picture...")

  savePicture("jpeg", 800, 600)
  savePicture("png", 800, 600)

  // When parameters with default values are not at the end of the function signature's parameter list,
  // you must explicitly name the arguments, because otherwise the compiler would assume the exact order of parameters.
  savePicture(width = 800, height = 600)

  // === SMART OPERATIONS ON STRINGS ===

  val str: String = "Hello, this is a scala note."

  // java built-in utility functions:
  println(str.charAt(2))                              // l
  println(str.substring(7, 11))                       // this
  println(str.split(" ").toList)               // List(Hello,, this, is, a, scala, note.)
  println(str.startsWith("Hello"))                    // true
  println(str.replace(" ", "-"))   // Hello,-this-is-a-scala-note.
  println(str.toLowerCase())                          // hello, this is a scala note.
  println(str.length)                                 // 28

  // scala-specific utility functions:
  val aNumberString = "45"
  val aNumber = aNumberString.toInt

  // prepending and appending with a character +: :+
  println('a' +: aNumberString :+ 'z')                // a45z
  println(str.reverse)                                // .eton alacs a si siht ,olleH
  println(str.take(2))                                // He

  // S-interpolators
  val name = "Hanzo"
  val age = 34
  val greeting = s"Hello, my name is $name and I am $age years old."
  val anotherGreeting = s"Hello, my name is $name and I am ${age + 1} years old."

  // F-interpolators
  val speed = 1.2f
  // %s tells that the parameter is of string format, %2.2f is 2 characters total, minimum 2 decimals precision
  val myth = f"$name%s can throw his kunai $speed%2.2f times per second."
  println(myth)                                       // Hanzo can throw his kunai 1.20 times per second.

  // Raw-interpolator: it prints text literally, does not escapes any control characters.
  println(raw"This is a \n new line")                 // This is a \n new line

  val escaped = "This is a \\n new line"
  println(raw"$escaped")                              // This is a \n new line
  // the above would not ignore the escaped control characters in the output in EARLIER scala versions!
}
