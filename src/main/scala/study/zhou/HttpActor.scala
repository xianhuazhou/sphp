package study.zhou

import akka.actor.Actor
import com.twitter.json.Json

class HttpActor extends Actor {
  def receive = {
    case HttpRequestData(channel, method, path, parameters) => { 
      Logger.log("send http request: [%s] -> %s ? %s" format (method, path, parameters.toString))
      try {
        val result = SPHP.request(method, path, parameters)
        Logger.log("processed data: %s" format Json.build(result))
        channel ! Json.build(result)
      } catch {
        case e: Exception => {
          channel ! "Failed from HttpActor"
          e.printStackTrace
        }
      }
    }

    case x: String => Logger.log("HttpActor received invalid message: %s" format x)
  }
}

// vim: set ts=2 sw=2 et:
