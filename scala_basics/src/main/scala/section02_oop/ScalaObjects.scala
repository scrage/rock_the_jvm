package section02_oop

object ScalaObjects extends App {

  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")!

  object Person { // this is a type, and also its only instance
    // class-level functionality, a singleton instance
    val N_EYES = 2
    def canFly: Boolean = false

    // singletons often have factory methods - and they're conveniently called apply
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(val name: String) {
    // instance-level functionality here,
  }
  // COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  // Scala object is a singleton instance!
  val mary = Person
  val john = Person
  println(mary == john) // true - the same singleton objects

  val bob = new Person("Bob")
  val alice = new Person("Alice")
  println(bob == alice) // false - different instances

  val bobbie = Person.apply(alice, bob)
  val lilBobbie = Person(alice, bob) // the factory method combined with the 'apply' looks like a constructor

  // SCALA APPLICATIONS
  // Scala Application = Scala object with
  // def main(args: Array[String]): Unit
}
