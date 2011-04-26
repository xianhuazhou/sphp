package study.zhou

import akka.actor.ActorRef

case class HttpRequestData(
  observer: ActorRef,
  method: String, 
  path: String, 
  parameters: Map[String, String], 
  needReply: Boolean
)

// vim: set ts=2 sw=2 et:
