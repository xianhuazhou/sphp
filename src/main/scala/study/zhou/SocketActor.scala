package study.zhou

import akka.actor.{ Actor, ActorRef, Channel }
import akka.dispatch._
import akka.camel._

import com.twitter.json.Json

class SocketActor(
  serviceHost: String, 
  servicePort: Int, 
  numberOfActors: Int) extends Actor with Consumer {

  type TASKS = List[Map[String, String]]
  type MapString = Map[String, String]

  private lazy val lbActor = Actor.actorOf(new LoadBalancerActor(numberOfActors)).start

  def endpointUri = "mina:tcp://%s:%d?textline=true" format (serviceHost, servicePort)

  def receive = {
    case msg: Message => {
      val data = msg.bodyAs[String]
      Logger.log("Received data: %s" format data)

      try {
        process(data)
      } catch {
        case e: Exception => {
          self.reply("[\"Failed from SocketActor\"]")
          Logger.log("Process data failed: %s" format e.toString)
          e.printStackTrace
        }
      }
    }
  }

  private def process(data: String) {
    val parameters: MapString = Json.parse(data).asInstanceOf[MapString]
    val tasks: TASKS = parameters.getOrElse("tasks", Map()).asInstanceOf[TASKS]
    val replyTimes = if (parameters.getOrElse("reply", true).asInstanceOf[Boolean]) tasks.size else 0
    val needReply = replyTimes > 0 
    if (!needReply) self.reply("[\"DONE\"]")

    val observer = Actor.actorOf(new Observer(self.channel, replyTimes)).start
    tasks.foreach(task => {
      val params = task.getOrElse("params", Map()).asInstanceOf[MapString]
      lbActor ! HttpRequestData(
        observer,
        task("method"), 
        task("path"), 
        params,
        needReply 
      )
    })
  }
}

// vim: set ts=2 sw=2 et:
