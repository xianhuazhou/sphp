package study.zhou

import akka.actor.Actor
import com.twitter.json.Json

class HttpActor extends Actor {
  def receive = {
    case HttpRequestData(observer, method, path, parameters, needReply) => { 
      Logger.log("send http request: [%s] -> %s ? %s" format (method, path, parameters.toString))
      try {
        val result = SPHP.request(method, path, parameters)
        Logger.log("processed data: %s" format result)
        if (needReply) {
          println("Reply to Observer")
          observer ! result 
        }
      } catch {
        case e: Exception => {
          observer ! "Failed from HttpActor"
          e.printStackTrace
        }
      }
    }

    case x: String => Logger.log("HttpActor received invalid message: %s" format x)
  }
}

// vim: set ts=2 sw=2 et:
