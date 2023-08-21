object ValuesVariablesTypes extends App {

  val x: Int = 42
  println(x)

  val y = 32

  // VALS ARE IMMUTABLE
  // COMPILER can infer types

  val aString: String = "hello";
  // semi-colons are allowed but not necessary in the language
  // only required when using multiple expressions in a single line, but that is an anti-pattern

  val aBoolean: Boolean = false
  val aChar: Char = 'X'
  val anInt: Int = x
  val aShort: Short = 4613
  val aLong: Long = 45390867254323567L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.1415

  // variables - actually an anti-pattern to use them in scala/functional programming
  var aVariable = 5 // side effects

  // prefer vals over vars
}
