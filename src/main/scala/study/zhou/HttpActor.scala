package study.zhou

import akka.actor.Actor
import com.twitter.json.Json

class HttpActor extends Actor {
  def receive = {
    case HttpRequestData(observer, method, path, parameters, needReply) => { 
      try {
        Logger.log("send http request: [%s] -> %s ? %s" format (method, path, parameters.toString))
        val result = SPHP.request(method, path, parameters)
        if (needReply) {
          observer ! result 
        }
      } catch {
        case e: Exception => {
          Logger.log("Failed from HttpActor")
          if (observer != null) {
            observer ! "Failed" 
          }
          e.printStackTrace
        }
      }
    }
  }
}

// vim: set ts=2 sw=2 et:
