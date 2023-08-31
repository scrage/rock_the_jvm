package section02_oop

import scala.language.postfixOps

object MethodNotations extends App {

  class Person(val name: String, favouriteMovie: String) {
    def likes(movie: String): Boolean = movie == favouriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}."
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}."
    def unary_! : String = s"$name, what the heck?"
    def isAlive: Boolean = true // a parameterless method can be used as postfix notation
    def apply(): String = s"Hi, my name is $name and I like $favouriteMovie."
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))

  // infix notation = operator notation (syntactic sugar)
  println(mary likes "Inception")

  val tom = new Person("Tom", "Fight Club")
  println(mary hangOutWith tom)
  println(mary + tom)
  println(mary.+(tom))

  // mathematical operators are actually acting the same way as methods, because they are
  println(1 + 2)
  println(1.+(2))

  // prefix notation
  val x = -1 // equivalent with .unary_-
  val y = 1.unary_-

  // unary_ prefix only works with - + ~ !

  println(!mary)
  println(mary.unary_!)

  // postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  // apply function to treat objects as methods
  println(mary.apply())
  println(mary()) // equivalent as above
}
