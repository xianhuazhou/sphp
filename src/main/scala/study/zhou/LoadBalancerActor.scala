package study.zhou

import akka.actor.{ Actor, ActorRef }
import akka.routing.{ LoadBalancer, CyclicIterator }

class LoadBalancerActor(actors: Int) extends Actor with LoadBalancer{
  val workers = List.fill(actors)(Actor.actorOf[HttpActor].start)
  val seq = new CyclicIterator[ActorRef](workers)
}

// vim: set ts=2 sw=2 et:
