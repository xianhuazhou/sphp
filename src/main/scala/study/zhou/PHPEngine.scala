package study.zhou

import com.caucho.quercus.Quercus
import com.caucho.quercus.QuercusContext

import com.caucho.vfs.Path
import com.caucho.vfs.StdoutStream
import com.caucho.vfs.WriteStream

class PHPEngine extends Quercus {
  val encoding = "utf-8"
  
  override def execute(path: Path): Unit = {
    val out = new WriteStream(StdoutStream.create())
    out.setNewlineString("\n");
    out.setEncoding(encoding);

    val env = createEnv(
      parse(path), 
      out, 
      new Request, 
      new Response(null)
    )

    val value = env.execute
    env.close

    out.flushBuffer
    out.free
  }
}

// vim: set ts=2 sw=2 et:
