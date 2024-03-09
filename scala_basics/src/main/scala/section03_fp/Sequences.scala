package section03_fp

import scala.util.Random

object Sequences extends App {

  // Seq
  // a very general interface for data structures that
  // - have a well defined order
  // - can be indexed
  // Seq supports various operations:
  //    apply, iterator, length, reverse for indexing and iterating,
  //    concatenation, appending, prepending, grouping, sorting,
  //    zipping, searching, slicing ...
  val aSequence = Seq(1, 3, 2, 4)
  println(aSequence)          // List(1, 3, 2, 4)
  println(aSequence.reverse)  // List(4, 2, 3, 1)
  println(aSequence(2)) // equivalent to aSequence.apply(2), returns the element on the 3rd index: 3
  println(aSequence ++ Seq(5, 6, 7)) // List(1, 3, 2, 4, 5, 6, 7)
  println(aSequence.sorted)         // List(1, 2, 3, 4)

  // Ranges
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(println)

  (1 to 10).foreach(println)    // prints numbers from 1 to 10 in new lines, INCLUSIVE
  (1 until 10).foreach(println) // prints numbers from 1 till 9 in new lines => NON-INCLUSIVE

  // ranges are a very useful data structure, because they can be used for scripting and automating steps
  // to print something 10 times to the console:
  (1 to 10).foreach(println)
  // can be used for mapping and filtering the range:
  (1 to 10).filter(x => x % 2 == 0).map(x => (x + 1) * 2).foreach(println)

  // Lists
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList
  // the above is syntactic sugar for calling the apply method on the :: class:
  // ::.apply(42, aList)
  println(prepended)                // List(42, 1, 2, 3)
  // another way to prepend a list, and also to append:
  println(24 +: aList :+ 42)        // List(24, 1, 2, 3, 42)

  // to construct a list of N elements of the same value:
  println(List.fill(5)("apple")) // List(apple, apple, apple, apple, apple)
  println(aList.mkString("-|-")) // prints: 1-|-2-|-3

  // Arrays
  // they are a direct mapping over Java's Array here
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3) // allocates memory for an array of 3 elements of type Int
  println(threeElements)          // prints: [I@735b478
  threeElements.foreach(println)  // prints three 0s -> Array.ofDim[T](n) fills  the allocated memory with default values (null, 0, false, ...)

  // arrays can be mutated in place!
  numbers(2) = 0    // a syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" "))  // prints: 1 2 0 4
  // the update method is rarely used in Scala, and is a similar syntactic sugar as apply
  val numbersSeq: Seq[Int] = numbers // this is legal, produces an ArraySeq (WrappedArray) through implicit conversion:
  println(numbersSeq)                // ArraySeq(1, 2, 0, 4)

  // Vectors
  // the default implementation for immutable sequences, have effectively constant indexed read and write: O(log32(n))
  // they have fast element addition (append/prepend), are implemented as fixed-branched trie (branch factor of 32), good performance for large sizes
  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector) // prints: Vector(1, 2, 3)

  val maxRuns = 1000
  val maxCapacity = 1000000

  // to benchmark the performance of the collections:
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  // to see how efficient a Vector is:
  val numbersList = (1 to maxCapacity).toList     // 3001812.464
  val numbersvector = (1 to maxCapacity).toVector // 1720.917

  // advantage of lists is that they keep the reference to tails - disadvantage is that updating an element in the middle takes long
  // advantage of vectors is that the depth of tree is small - disadvantage is that they need to replace an entire 32-element chunk

  println(getWriteTime(numbersList))
  println(getWriteTime(numbersvector))

}
