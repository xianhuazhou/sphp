package study.zhou

import org.eclipse.jetty.server.{ Request => JettyRequest, Response => JettyResponse, HttpConnection }

import javax.servlet.http.Cookie
import javax.servlet.http.Part
import javax.servlet.http.HttpServletResponse

class Request extends JettyRequest {

  class SPHPEnumeration[T] extends java.util.Enumeration[T] {
    override def hasMoreElements: Boolean = false
    override def nextElement: T = List[T]().head
  }

  override def getCookies: Array[Cookie] = null
  def getPart(name: String): Part = null
  def getParts(): java.util.Collection[Part] = null
  def logout(): Unit = {}
  def login(name: String, pass: String): Unit = {}
  def authenticate(response: HttpServletResponse): Boolean = true
  override def isSecure(): Boolean = false 
  override def getHeader(name: String): String = null 
  override def getHeaderNames(): java.util.Enumeration[String] = new SPHPEnumeration[String] 
  override def getHeaders(name: String): java.util.Enumeration[String] = new SPHPEnumeration[String]
  override def getServerName(): String = "SPHP" 
  override def getRealPath(path: String): String = path 
}

class Response(connection: HttpConnection) extends JettyResponse(connection: HttpConnection) {
  def getHeaderNames: java.util.Collection[String] = null
  override def setStatus(sc: Int, sm: String): Unit = {} 
}

// vim: set ts=2 sw=2 et:
