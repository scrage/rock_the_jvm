package section03_fp

object MapFlatmapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  println(list.filter(_ % 2 == 0))

  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // all combinations between lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  // the "right" way to do ITERATIONS in FP - a series of flatMaps and a final map at the end
  val combinations = numbers.flatMap(n => chars.flatMap(c => colors.map(color => n.toString + c + "-" + color)))
  println(combinations)
  // the above can become increasingly harder to read, but for-comprehensions remedy this issue

  // FOREACH
  list.foreach(println)

  // for-comprehensions - this expression will be rewritten by the compiler to a series of flatMaps and a final map
  val forCombinations = for {
    n <- numbers
    c <- chars
    color <- colors
  } yield n.toString + c + "-" + color
  println(forCombinations)

  // we can filter out some values using a guard
  val filteredForCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield n.toString + c + "-" + color
  println(filteredForCombinations)

  // syntax overload
  list.map { x =>
    x * 2
  }

}
