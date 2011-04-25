package study.zhou

import akka.actor.{ Actor, ActorRef }
import akka.dispatch._
import akka.camel._

import com.twitter.json.Json

class SocketActor(
  serviceHost: String, 
  servicePort: Int, 
  numberOfActors: Int) extends Actor with Consumer {
  private lazy val lbActor = Actor.actorOf(new LoadBalancerActor(numberOfActors)).start

  def endpointUri = "mina:tcp://%s:%d?textline=true" format (serviceHost, servicePort)

  def receive = {
    case msg: Message => {
      self.reply("Done\n")

      val data = msg.bodyAs[String]
      Logger.log("-" * 20)
      Logger.log("received: %s" format data)
      Logger.log("-" * 20)

      try {
        process(data)
      } catch {
        case e: Exception => e.printStackTrace
      }
    }

    case result: String => {
      println("Processed result: %s" format result)
    }
  }

  private def process(data: String) {
    val tasks: List[Map[String, String]] = Json.parse(data).asInstanceOf[List[Map[String, String]]]
    tasks.foreach(task => {
      val params = task.getOrElse("params", Map[String, String]()).asInstanceOf[Map[String, String]]
      lbActor ! HttpRequestData(
        task("method"), 
        task("path"), 
        params
      )
    })
  }
}

// vim: set ts=2 sw=2 et:
