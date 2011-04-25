package study.zhou

import akka.actor.Actor

class HttpActor extends Actor {
  def receive = {
    case HttpRequestData(method, path, parameters) => { 
      Logger.log("send http request: [%s] -> %s ? %s" format (method, path, parameters.toString))
      try {
        val result = SPHP.request(method, path, parameters)
        self.reply(result)
      } catch {
        case e: Exception => e.printStackTrace
      }
    }

    case x: String => Logger.log("HttpActor received invalid message: %s" format x)
  }
}

// vim: set ts=2 sw=2 et:
