package study.zhou

import akka.actor.Actor
import akka.actor.ActorRef

import akka.routing.{ LoadBalancer, CyclicIterator }

class MyActor extends Actor {
  def receive = {
    case args: String => {
      println(SPHP.execute("/get.php?" + args))
    }
  }
}

class LBActor extends Actor with LoadBalancer{
  val actors = List.fill(3)(Actor.actorOf[MyActor].start)
  val seq = new CyclicIterator[ActorRef](actors)
}

object App {
  def main(argv: Array[String]) {
    SPHP.init("/var/www/html/test")
    val lb = Actor.actorOf[LBActor].start
    for (i <- 1 to 10) lb ! ("a=%s" format i)
  }
}


// vim: set ts=2 sw=2 et:
