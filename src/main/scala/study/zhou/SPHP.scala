package study.zhou

import com.caucho.resin.{ ResinEmbed, WebAppEmbed, HttpEmbed }
import java.util.ArrayList

import org.apache.http.NameValuePair
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.client.methods.{ HttpGet, HttpPost }
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.params.BasicHttpParams
import org.apache.http.message.BasicNameValuePair

import akka.actor.{ Actor, ActorRef }
import akka.camel.CamelServiceManager

object SPHP {
  private val resin = new ResinEmbed
  private val _encoding: String = Config.encoding
  private var _host: String = _ 
  private var _port: Int = _ 
  lazy val socketActor: ActorRef = Actor.actorOf(new SocketActor(
    Config.serviceHost, 
    Config.servicePort, 
    Config.actors,
    Config.maxLineLength)
  )

  def start {
    // start resin
    _host = "http://" + Config.httpHost
    _port = Config.httpPort 
    resin.addWebApp(new WebAppEmbed("/", Config.docRoot))
    resin.addPort(new HttpEmbed(_port))
    resin.start

    // start camel service
    socketActor.start
    CamelServiceManager.startCamelService

    Logger.log("-" * 20)
    Logger.log("\tSPHP is running: ")
    Logger.log("\t  http : %s:%d" format (_host, _port))
    Logger.log("\t  camel: %s:%d" format (Config.serviceHost, Config.servicePort))
    Logger.log("\t  actors: %d" format (Config.actors))
    Logger.log("-" * 20)
  } 

  // handle http requests (GET or POST)
  def request(method: String, path: String, parameters: Map[String, String]): String = {
    val httpClient = new DefaultHttpClient
    val responseHandler = new BasicResponseHandler
    var result = ""
    var uri = "%s:%d%s" format (_host, _port, path)

    val params = new ArrayList[NameValuePair]
    parameters.foreach(p => 
      params.add(new BasicNameValuePair(String.valueOf(p._1), String.valueOf(p._2)))
    )

    // get
    if (method == "get") {
      uri += (if (uri.contains("?")) "&" else "?") + 
        URLEncodedUtils.format(params, _encoding)
      result = httpClient.execute(new HttpGet(uri), responseHandler)

    // post
    } else {
      val req = new HttpPost(uri)
      req.setEntity(new UrlEncodedFormEntity(params, _encoding))
      result = httpClient.execute(req, responseHandler)
    }

    httpClient.getConnectionManager.shutdown

    return result
  }
}

// vim: set ts=2 sw=2 et:
