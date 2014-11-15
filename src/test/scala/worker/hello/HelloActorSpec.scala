package worker.hello

import org.scalatest.{ BeforeAndAfterAll, FlatSpecLike, Matchers }
import akka.actor.{ Actor, Props, ActorSystem }
import akka.testkit.{ ImplicitSender, TestKit, TestActorRef }
import scala.concurrent.duration._

class GreeterSpec (_system: ActorSystem) extends TestKit (_system)
  with ImplicitSender
  with Matchers
  with FlatSpecLike
  with BeforeAndAfterAll() {

  def this() = this(ActorSystem("HelloAkkaSpec"))

  override def afterAll: Unit = {
    system.shutdown()
    system.awaitTermination(10.seconds)
  }

  "An HelloActorActor" should "be able to set a new greeting" in {
    val greeter = TestActorRef (Props[Greeter])
    greeter ! WhoToGreet("test")
    greeter.underlyingActor.asInstanceOf[Greeter].greeting should be ("hello, test")
  }

  it should "be able to get a new greeting" in {
    val greeter = system.actorOf (Props[Greeter], "greeter")
    greeter ! WhoToGreet ("test")
    greeter ! Greet
    expectMsgType[Greeting].message.toString should be ("hello, test")
  }
}
