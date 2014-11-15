package worker.hello

import akka.actor.Actor

case object Greet
case class WhoToGreet(who: String)
case class Greeting(message: String)

class Greeter extends Actor {
  var greeting = "hello anonomous!"

  def receive = {
    case WhoToGreet(who) => greeting = s"hello, $who"
    case Greet => { 
      println(greeting)
      sender ! Greeting(greeting)
    }
  }
}
