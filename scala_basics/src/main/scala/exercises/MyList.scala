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
}
