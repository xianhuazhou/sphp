package study.zhou

import com.caucho.vfs.FilePath

object SPHP {
  val engine = new PHPEngine
  val quercus = engine.getQuercus

  def init {
    quercus.init
    quercus.start
    quercus.setIniFile(new FilePath("/home/zhou/maven/sphp/src/data/php.ini"))
    quercus.setIni("register_argc_argv", "on")
  }

  def runFile(file: String) {
    try {
      engine.executeFile(file)
    } catch {
      case e: Exception => {
        println("Exception: ")
        e.printStackTrace
      }
    }
  }

  def runCode(code: String) {
    engine.execute(code)
  }
}


// vim: set ts=2 sw=2 et:
