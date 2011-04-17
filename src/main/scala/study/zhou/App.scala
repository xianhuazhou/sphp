package study.zhou

import com.caucho.quercus.QuercusEngine
import akka.actor.Actor
import akka.actor.ActorRef

import akka.routing.{ LoadBalancer, CyclicIterator }

object Utils {
  val engine = new QuercusEngine
  val quercus = engine.getQuercus

  def initPHPEngine {
    quercus.init
    quercus.start
  }

  def runPHPFile(file: String) {
    engine.executeFile(file)
  }

  def runPHPCode(code: String) {
    engine.execute(code)
  }
}

class MyActor extends Actor {
  def receive = {
    case args: String => {
      Utils.runPHPFile("/home/zhou/maven/sphp/src/data/my.php")
    }
  }
}

class LBActor extends Actor with LoadBalancer{
  val actors = List.fill(3)(Actor.actorOf[MyActor].start)
  val seq = new CyclicIterator[ActorRef](actors)
}

object App {
  def main(argv: Array[String]) {
    Utils.initPHPEngine

    val lb = Actor.actorOf[LBActor].start
    (1 to 10).foreach(x => 
      lb ! " some args here "
    )

    argv.foreach(println)
  }
}


// vim: set ts=2 sw=2 et:
