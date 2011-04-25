package study.zhou

import scala.xml.XML

object Config {
  lazy private val xml = XML.load(new java.net.URL("file:///opt/sphp/sphp.xml"))
  lazy val httpHost = query("http", "host")
  lazy val httpPort = query("http", "port").toInt
  lazy val docRoot= query("http", "doc_root") 
  lazy val servicePort = query("service", "port").toInt
  lazy val actors = query("service", "actors").toInt

  def query(items: String*): String = {
    var node = xml \ items.head
    items.tail.foreach(item => node \= item )
    node.text
  }
}

// vim: set ts=2 sw=2 et:
