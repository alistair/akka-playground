package worker

import scala.util._
import akka.actor.{Actor,ActorRef, Props}
import spray.routing._
import spray.http._
import spray.json._
import spray.httpx.SprayJsonSupport
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling.MetaMarshallers
import MediaTypes._

import hello._

object GreeterJsonSupport extends DefaultJsonProtocol with SprayJsonSupport with MetaMarshallers {
  implicit val GreetingFormats = jsonFormat1(Greeting)
  implicit val WhoToGreetFormat = jsonFormat1(WhoToGreet)
}

class FrontWorkerActor extends Actor with FrontService {
  def actorRefFactory = context
  def receive = runRoute (myRoute)

  def createGreeterActor: ActorRef = context.actorOf(Props[Greeter])
}

trait FrontService extends HttpService {
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  import scala.concurrent.Future

  import GreeterJsonSupport._

  def createGreeterActor: ActorRef

  val helloActor = createGreeterActor
  implicit val timeout = Timeout(2.seconds)

  implicit lazy val executionContext = actorRefFactory.dispatcher
 
  
  val myRoute = 
    path ("") {
      get {
        respondWithMediaType (`text/html`) {
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    } ~
    path("greet") {
      get {
        complete {(helloActor ? Greet).mapTo[Greeting] }
      }
    } ~
    path("greet" / "who") {
      post {
        handleWith { g: WhoToGreet => println("hello"); helloActor !g; (helloActor ? Greet).mapTo[Greeting] }
      }
    }
}


