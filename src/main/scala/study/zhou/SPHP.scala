package study.zhou

import com.caucho.resin._

object SPHP {
  val resin = new ResinEmbed

  def init(root: String) {
    val webApp = new WebAppEmbed("/", root)
    resin.addWebApp(webApp)
    resin.start
  }

  def execute(file: String): String = {
    resin.request("GET " + file)
  }
}


// vim: set ts=2 sw=2 et:
