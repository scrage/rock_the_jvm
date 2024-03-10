package section03_fp

object TuplesAndMaps extends App {

  // tuples are basically finite ordered lists in Scala as well
  val aTupleLongForm = new Tuple2(2, "hello Scala") // Tuple2[Int, String] = (Int, String)
  val aTuple = (2, "hello Scala")

  // accessing a tuple's elements
  println(aTuple._1) // 2
  println(aTuple._2) // hello Scala

  // it has a copy method just like in the case of lists
  println(aTuple.copy(_2 = "goodbye Scala")) // (2,goodbye Scala)

  // it has a swap method that swaps the elements in place
  println(aTuple.swap) // (hello Scala,2)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), ("Daniel", 789))
  // a -> b is syntactic sugar for (a, b) here
  val phoneBook2 = Map("Jim" -> 555, "Daniel" -> 789)

  println(phoneBook.contains("Jim")) // prints: true
  // println(phoneBook("Mary")) would throw a NoSuchElementException
  // to guard against compile time exceptions, we can use default values

  val aGuardedPhoneBook = Map("Jim" -> 555, "Daniel" -> 789).withDefaultValue(-1)
  println(aGuardedPhoneBook("Mary")) // prints: -1

  // adding new pairs to Maps
  val newPairing = "Mary" -> 678
  val newPhoneBook = phoneBook + newPairing

  // functions on Maps
  // map, flatMap, filter
  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))
  // prints: Map(jim -> 555, daniel -> 789)

  println(phoneBook.view.filterKeys(x => x.startsWith("J"))) // prints: MapView(<not computed>) !
  println(phoneBook.view.mapValues(number => "0235-" + number * 10))

  // conversions to other collections
  println(phoneBook.toList)             // List((Jim,555), (Daniel,789))
  println(List(("Daniel", 555)).toMap)  // Map(Daniel -> 555)

  val names = List("Bob", "James", "Angela", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
  // prints: HashMap(J -> List(James, Jim), A -> List(Angela), B -> List(Bob), D -> List(Daniel))

}
