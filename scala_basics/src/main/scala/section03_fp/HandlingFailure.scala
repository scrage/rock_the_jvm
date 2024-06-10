package section03_fp

import java.util.Random
import scala.util.{Failure, Success, Try}

object HandlingFailure extends App {

  // either create success and failure explicitly:
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("runtime failure"))

  println(aSuccess)
  println(aFailure)

  // or since the Try companion object's apply method takes care of them...
  def unsafeMethod(): String = throw new RuntimeException("no string here")

  // rely on Try objects via their apply method:
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  // syntax sugar
  val anotherPotentialFailure = Try {
    // code that might throw
  }

  // utilities
  println(potentialFailure.isSuccess)
  println(potentialFailure.isFailure)

  // orElse for chaining
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  // when designing APIs, use Try with Success and Failure:
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException())
  def betterFallbackMethod(): Try[String] = Success("A valid result")
  def betterFallback = betterUnsafeMethod() orElse(Try(backupMethod()))

  // Try also has map, flatMap and filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))

  // filtering is the only thing that might turn a Success into a Failure
  println(aSuccess.filter(_ > 10))
  // Failure(java.util.NoSuchElementException: Predicate does not hold for 3)

  // => for-comprehensions

  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    // writing a safe API to contain the possible exceptions:
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }

    // writing a safe API to contain the possible exceptions:
    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  val possibleConnection = HttpService.getSafeConnection(hostname, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  // shorthand version for chaining all calls:
  HttpService.getSafeConnection(hostname, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for-comprehension version:
  for {
    connection <- HttpService.getSafeConnection(hostname, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)
}
