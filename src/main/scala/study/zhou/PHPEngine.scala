package study.zhou

import java.io.OutputStream

import com.caucho.quercus.QuercusEngine
import com.caucho.quercus.QuercusContext

import com.caucho.quercus.env.NullValue
import com.caucho.quercus.env.Value
import com.caucho.quercus.env.Env
import com.caucho.quercus.env.BooleanValue

import com.caucho.quercus.page.InterpretedPage
import com.caucho.quercus.page.QuercusPage
import com.caucho.quercus.parser.QuercusParser
import com.caucho.quercus.program.QuercusProgram
import com.caucho.vfs.StdoutStream

import com.caucho.vfs.Path
import com.caucho.vfs.WriteStream

class PHPEngine(val phpQuercus: QuercusContext) extends QuercusEngine {
  
  def this() = this(new QuercusContext) 

  override def getQuercus: QuercusContext = phpQuercus

  override def execute(path: Path): Value = {
    val reader = path.openRead()
    val program = QuercusParser.parse(phpQuercus, null, reader)
    val out = new WriteStream(StdoutStream.create())
    out.setNewlineString("\n");
    out.setEncoding("utf-8");
    val env = phpQuercus.createEnv(
      new InterpretedPage(program), 
      out, 
      new Request, 
      new Response(null)
    ) 
    val value = program.execute(env)

    out.flushBuffer
    out.free

    return value
  }
}

// vim: set ts=2 sw=2 et:
