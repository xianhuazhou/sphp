package study.zhou

import akka.actor.ActorRef
import akka.actor.Channel

case class HttpRequestData(channel: Channel[Any], method: String, path: String, parameters: Map[String, String])

// vim: set ts=2 sw=2 et:
