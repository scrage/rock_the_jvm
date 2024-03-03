package section02_oop

object CaseClasses extends App {
  case class Person(name: String, age: Int)

  // 1. case class parameters become fields
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. they get a sensible toString
  // println(instance) == println(instance.toString) - it's a syntactic sugar
  println(jim.toString)
  // prints "Person(Jim,34)"

  // 3. equals and hashCode implemented out of the box with case classes
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)
  // true

  // 4. case classes have a handy copy method
  val jim3 = jim.copy() // creates a new instance of the case class
  val jim4 = jim.copy(age = 45)

  // 5. case classes have companion objects, which have handy factory methods
  val thePerson = Person
  val mary = Person("Mary", 23) // this delegates to the companion object's apply method
  // the apply method does the same thing as the constructor
  // ...and this is how we omit the new keyword around case classes

  // 6. case classes are serializable - which makes them especially usable for distributed systems
  // Akka relies on them

  // 7. case classes have extractor patterns => they can be used in pattern matching

  // a case object acts like a case class
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }
}
