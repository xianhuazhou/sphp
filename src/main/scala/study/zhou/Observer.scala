package study.zhou

import akka.actor.{ Actor, Channel }
import com.twitter.json.Json

class Observer(socketActor: Channel[Any], replyTimes: Int) extends Actor {
  var results = new scala.collection.mutable.ListBuffer[String]()
  var responseTimes = 0

    def receive = {
      case result: String => {
        responseTimes += 1
        Logger.log("received from HttpActor in Observer")

        if (result != "") {
          results += result
        }

        if (responseTimes == replyTimes) {
          socketActor ! Json.build(results)
          Logger.log("Observer is sending response to client")
          self.stop
        }
      }
  }
}

// vim: set ts=2 sw=2 et:
