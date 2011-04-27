package study.zhou

import scala.xml.XML

object Config {
  lazy private val xml = XML.load(new java.net.URL("file:///opt/sphp/sphp.xml"))
  val httpHost = query("http", "host")
  val httpPort = query("http", "port").toInt
  val encoding = query("http", "encoding")
  val docRoot= query("http", "docRoot") 

  val isDebugMode= query("debug") == "true"

  val serviceHost = query("service", "host")
  val servicePort = query("service", "port").toInt
  val actors = query("service", "actors").toInt
  val maxLineLength = query("service", "maxLineLength").toInt

  def query(items: String*): String = {
    var node = xml \ items.head
    items.tail.foreach(item => node \= item )
    node.text
  }
}

// vim: set ts=2 sw=2 et:
