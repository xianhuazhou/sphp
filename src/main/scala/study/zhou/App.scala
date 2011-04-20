package study.zhou

import akka.actor.Actor
import akka.actor.ActorRef

import akka.routing.{ LoadBalancer, CyclicIterator }

class MyActor extends Actor {
  def receive = {
    case args: String => {
      SPHP.runFile("/home/zhou/maven/sphp/src/data/my.php")
    }
  }
}

class LBActor extends Actor with LoadBalancer{
  val actors = List.fill(3)(Actor.actorOf[MyActor].start)
    val seq = new CyclicIterator[ActorRef](actors)
  }

object App {
  def main(argv: Array[String]) {
    SPHP.init
    SPHP.runFile("/home/zhou/maven/sphp/src/data/my.php")

    /*
    val lb = Actor.actorOf[LBActor].start
    (1 to 10).foreach(x => 
      lb ! " some args here "
    )

    argv.foreach(println)
    */
  }
}


// vim: set ts=2 sw=2 et:
