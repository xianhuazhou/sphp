package study.zhou

import com.caucho.vfs.FilePath
import com.caucho.vfs.StringPath

object SPHP {
  val engine = new PHPEngine

  def init {
    engine.init
    engine.start
    engine.setIniFile(new FilePath("/home/zhou/maven/sphp/src/data/php.ini"))
    engine.setIni("register_argc_argv", "on")
  }

  def runFile(file: String) {
    try {
      engine.execute(new FilePath(file))
    } catch {
      case e: Exception => {
        println("Exception: ")
        e.printStackTrace
      }
    }
  }

  def runCode(code: String) {
    engine.execute(new StringPath(code))
  }
}


// vim: set ts=2 sw=2 et:
