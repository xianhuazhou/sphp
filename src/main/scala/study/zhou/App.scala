package study.zhou

import com.caucho.quercus.QuercusEngine;

object App {
  def main(argv: Array[String]) {
    println("hello")
    val engine: QuercusEngine = new QuercusEngine
    engine.getQuercus.init
    engine.getQuercus.start
    engine.executeFile("/home/zhou/test.php")
  }
}


// vim: set ts=2 sw=2 et:
