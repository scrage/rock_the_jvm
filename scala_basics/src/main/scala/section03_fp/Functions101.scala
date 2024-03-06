package section03_fp

object Functions101 extends App {

  // in Scala, every function is an object (an instance of a particular class)
  // so OOP prevents us to use functions as "first class elements" in Scala,
  // but it has a workaround for this restriction enforced by the JVM

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(3))

  // but Scala supports these function types out of the box with up until 22 parameters
  // syntactic sugar for this is val stringToIntConverter: String => Int
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }

  println(stringToIntConverter("3") + 4)

  // val adder: (Int, Int) => Int
  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  println(adder(10, 5))

  // all functions in Scala are objects, instances of classes Function[1..22]

  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }
  println(concatenator("hello ", "there"))

  // a higher-order function
  // Function1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function[Int, Function1[Int, Int]] {
    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }
  
  val adder3 = superAdder(3)
  println(adder3(4))          // 7
  println(superAdder(5)(10))  // 15 - a curried function call

}

// in practice, every class' apply method will be invoked when the class' instance
// is being used _as a function_
trait MyFunction[A, B] {
  def apply(element: A): B
}