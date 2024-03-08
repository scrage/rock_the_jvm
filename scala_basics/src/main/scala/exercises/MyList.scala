package exercises

// a linked list
abstract class MyList[+A] {

  /*
    head = first element of the list
    tail = remainder of the list
    isEmpty = is this list empty
    add(int) => new list with this element of the list
    toString => a string representation of the list
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A] (element: B): MyList[B]
  def printElements: String

  // polymorphic call
  override def toString: String = "[" + printElements + "]" // this approach delegates the printing strategy to the subclass' implementation

  // higher-order functions (either receive functions as parameters or return functions as results)
  def map[B] (transformer: A => B): MyList[B]
  def flatMap[B] (transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]

  // more HOFs
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B

}

// objects can extend classes
case object Empty extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing] (element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty

  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing] (list: MyList[B]): MyList[B] = list

  // HOFs
  def foreach(f: Nothing => Unit): Unit = ()

  def sort(compare: (Nothing, Nothing) => Int) = Empty

  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length!")
    else Empty

  def fold[B](start: B)(operator: (B, Nothing) => B): B = start // is often called reduce - at least one of the forms reduce can have

}

case class Cons[+A] (h: A, t: MyList[A]) extends MyList[A] {

  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A] (element: B): MyList[B] = new Cons(element, this)
  def printElements: String =
    if(t.isEmpty) "" + h
    else h + " " + t.printElements

  def filter(predicate: A => Boolean): MyList[A] =
    if (predicate(h)) new Cons(h, t.filter(predicate)) // predicate.apply(h)
    else t.filter(predicate)

  def map[B](transformer: A => B): MyList[B] =
    new Cons(transformer(h), t.map(transformer)) // transformer.apply(h)

  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  def flatMap[B] (transformer: A => MyList[B]): MyList[B] =
    transformer(h) ++ t.flatMap(transformer) // transformer.apply(h)

  // HOFs
  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length!")
    else new Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  /*
    [1, 2, 3].fold(0)(+) =
    = [2, 3].fold(1)(+) =
    = [3].fold(3)(+) =
    = [].fold(6)(+) =
    = 6
   */
  def fold[B](start: B)(operator: (B, A) => B): B = {
//    val newStart = operator(start, h)
//    t.fold(newStart)(operator)
    t.fold(operator(start, h))(operator)
  }

}

//trait MyPredicate[-T] {
//  def test(elem: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def transform(elem: A): B
//}

object ListTest extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfOtherIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))
  val listOfStrings: MyList[String] = new Cons("hello", new Cons("there", Empty))

  println(listOfIntegers)
  // [1 2 3]

  println(listOfStrings)
  // [hello there]

//  println(listOfIntegers.map(new Function1[Int, Int] {
//    override def apply(elem: Int): Int = elem * 2
//  }).toString)
//  println(listOfIntegers.map(x => x * 2).toString)
  println(listOfIntegers.map(_ * 2).toString)
  // [2 4 6]

//  println(listOfIntegers.filter(new Function1[Int, Boolean] {
//    override def apply(elem: Int): Boolean = elem % 2 == 0
//  }).toString)
//  println(listOfIntegers.filter(x => x % 2 == 0).toString)
  println(listOfIntegers.filter(_ % 2 == 0).toString)
  // [2]

  println((listOfIntegers ++ listOfOtherIntegers).toString)
  // [1 2 3 4 5]

//  println(listOfIntegers.flatMap(new Function1[Int, MyList[Int]] {
//    override def apply(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem + 1, Empty))
//  }).toString)
  println(listOfIntegers.flatMap(x => new Cons(x, new Cons(x + 1, Empty))).toString)
  // [1 2 2 3 3 4]

  listOfIntegers.foreach(println)
  println(listOfIntegers.sort((x, y) => y - x))
  // prints: [3 2 1]
  println(listOfOtherIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
  // prints: [4-hello 5-there]
  println(listOfIntegers.fold(0)(_ + _))
  // prints: 6

  // for-comprehensions also work on the custom list class
  for {
    n <- listOfIntegers
    string <- listOfStrings
  } yield n + "-" + string

}
