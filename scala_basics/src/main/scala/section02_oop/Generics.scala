package section02_oop

object Generics extends App {

  class MyList[A] {
    // use type A
  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }

  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. covariance: List[Cat] extends List[Animal]
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // covariance answers yes to a hard question: would animalList.add(new Dog) be legal? (only when turning it into a list of Animals)

  // 2. invariance answers no to the previous question:
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal] // new InvariantList[Cat] is illegal

  // 3. contravariance uses the derived classes and is a bit counter-intuitive at first (at least for lists):
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]
  class Trainer[-A]
  val trainerList: Trainer[Cat] = new Trainer[Animal]
  // the above expresses the idea of supplying a trainer of Animals for the purpose of training Cats
  // it is the opposite relation to covariance

  // bounded types
  class Cage[A <: Animal] (animal: A) // class Cage only accepts type parameters A which are sub-types of Animal
  val cage = new Cage(new Dog) // an upper-bounded type of Animal
  // A >: B expresses that the class only accepts types A which are super-type of class B; lower-bounded type of B

  // when we need to deal with adding other sub-types of a super class to a list of a different sub-types:
  class AbstractList[+A] {
    def add[B >: A] (element: B): AbstractList[B] = ???
    /*
    A = Cat
    B = Dog = Animnal
    when adding a new element of Cat to a list of Dogs, then it will turn it into a list of Animals
     */
  }
}
