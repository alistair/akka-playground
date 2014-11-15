package worker

import akka.actor.{ ActorRef, Props, ActorSystem }
import akka.testkit.{ ImplicitSender, TestKit, TestActorRef }
import spray.testkit.ScalatestRouteTest
import spray.routing.Directives
import org.scalatest.{ FlatSpecLike }
import org.scalatest.Matchers._
import spray.http.HttpResponse
import spray.http.MediaTypes._
import spray.http.ContentType
import spray.http.HttpCharsets._

import hello._

class FrontServiceSpec(_system: ActorSystem) extends TestKit(_system) 
  with FlatSpecLike 
  with Directives 
  with ScalatestRouteTest 
  with FrontService {
  
  override val system = _system

  implicit val actorRefFactory = system

  def createGreeterActor: ActorRef = TestActorRef[Greeter]

  "The FrontService" should "have a resolve a root url" in {
    Get() ~> myRoute ~> (_.response) === HttpResponse()
  }

  it should "contain 'Say hello to'" in {
    Get() ~> myRoute ~> check {
      responseAs[String] should include("Say hello to")
    }
  }

  it should "have a mime type of text/html" in {
    Get() ~> myRoute ~> check {
      contentType should equal (ContentType(`text/html`,`UTF-8`))
    }
  }

  it should "have a greet route" in {
    Get("/greet") ~> myRoute ~> (_.response) === HttpResponse()
  }

}
