package section04_pattern_matching

import scala.util.Random

object PatternMatching extends App {

  // pattern matching is a switch on steroids
  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"          // _ = WILDCARD
  }

  println(x)
  println(description)

  // properties of pattern matching:
  // 1. decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    // 2. add guards (cases are matched in order)
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink alcohol in the US."
    case Person(n, a) => s"Hi, my name is $n and I am $a years old."
    case _ => "I don't know who I am."
  }
  println(greeting)

  // if no cases match, then it will throw a scala.MatchError - so use _ as a best practice!
  // type of the PM expression will be the lowest common ancestor type of all the cases that they return (unified type of all the types of the cases)

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  val animal: Animal = Dog("Terra Nova")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed.")
  } // compiler would warn us that it would fail on the Animal() and Parrot(_) inputs - but no warnings on non-sealed hierarchies

  // PM works really well with case classes, as case classes come with extractor patterns out of the box

  /*
    Exercise
    simple function that uses PM:
      takes an Expr => returns humanly readable string

      Sum(Number(2), Number(3)) => 2 + 3
      Sum(Sum(Number(2), Number(3)), Number(4)) => 2 + 3 + 4
      Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
      Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3
   */

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))
  println(show(Prod(Sum(Number(2), Number(1)), Prod(Number(3), Number(5)))))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(5)))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))

  /*
    2 + 3 + 4
    (2 + 1) * 3
    (2 + 1) * 3 * 5
    (2 + 1) * (3 + 5)
    2 * 1 + 3
   */
}
