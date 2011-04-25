package study.zhou

import akka.actor.Actor
import akka.actor.ActorRef

import akka.routing.{ LoadBalancer, CyclicIterator }

case class HttpRequestData(method: String, path: String, parameters: Map[String, String])

class HttpActor extends Actor {
  def receive = {
    case HttpRequestData(method, path, parameters) => { 
      val result = SPHP.request(method, path, parameters)
      println(result)
    }
  }
}

class LBActor extends Actor with LoadBalancer{
  val actors = List.fill(Config.actors)(Actor.actorOf[HttpActor].start)
  val seq = new CyclicIterator[ActorRef](actors)
}

object App {
  def main(argv: Array[String]) {
    SPHP.init("http://" + Config.httpHost, Config.httpPort, Config.docRoot)
    val lb = Actor.actorOf[LBActor].start
    lb ! HttpRequestData("post", "/post.php", Map("a" -> "A", "b" -> "B"))
  }
}


// vim: set ts=2 sw=2 et:
