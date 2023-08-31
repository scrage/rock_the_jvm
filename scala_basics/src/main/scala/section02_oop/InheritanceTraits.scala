package section02_oop

object InheritanceTraits extends App {

  class Animal {
    // if this were private, the subclasses would not access this method
    def eat = println("omnomnom")
    val creatureType = "wild"
  }

  // Single class inheritance:
  // Cat is a subclass of Animal, Animal is a superclass of Cat
  class Cat extends Animal {
    def crunch = {
      eat   // if eat would be private in the Animal class, it would be only usable here, but not on the instance below
      println("crunch crunch")
    }
  }

  val cat = new Cat
  cat.eat
  cat.crunch

  // constructors
  class Person(name: String, age: Int)
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)   // parameters must be passed to the constructor

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("crunch, crunch")
    }

    // override val creatureType: String = "domestic"
  }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  // type substitution (polymorphism in a broad sense)
  val unknownAnimal: Animal = new Dog("K0")
  unknownAnimal.eat // omnomnom \n crunch, crunch, due to using 'super'

  // preventing overrides
  // 1 - use final on member
  // 2 - use final on class
  // 3 - seal the class (can extend classes in this file only, but prevents extension in other files)
}
