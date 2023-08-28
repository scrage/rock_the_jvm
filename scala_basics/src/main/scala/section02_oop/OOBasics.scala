package section02_oop

object OOBasics extends App {

  val person = new Person("Hanzo Hasashi", 34)
  println(person.age)
  println(person.x)
  person.greet("Bi Han") // without the this keyword, it will print: "Bi Han says: Hi, Bi Han!"
  person.greet()
}

// constructor (with class parameters)
class Person(name: String, val age: Int = 0) {
  // body
  val x = 2
  println(1 + 3)

  // method
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name!")

  // overloading - same method names, different signatures
  def greet(): Unit = println(s"Hi, I am $name!") // the 'this' keyword is implied in this case by default

  // overloading constructors
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")

  // in scala, overloaded constructors can only call other constructors, which makes them impractical for many cases
  // other than using them for default parameters
  // but we can also just pass a default value to the classes' parameter list, which makes auxiliary constructors useless

  // the value of these code blocks is ignored in case of classes
}

// class parameters are NOT FIELDS!