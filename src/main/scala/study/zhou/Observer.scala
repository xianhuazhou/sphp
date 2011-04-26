package study.zhou

import akka.actor.{ Actor, Channel }
import com.twitter.json.Json

class Observer(socketActor: Channel[Any], replyTimes: Int) extends Actor {
  var results = new scala.collection.mutable.ArrayBuffer[String]()

    def receive = {
      case message: String => {
        Logger.log("received from HttpActor in Observer")
        results += message
        if (results.size == replyTimes) {
          Logger.log("Observer is sending response to client")
          socketActor ! Json.build(results)
          self.stop
        }
      }
  }
}

// vim: set ts=2 sw=2 et:
