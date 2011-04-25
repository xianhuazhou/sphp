package study.zhou

import com.caucho.resin.{ ResinEmbed, WebAppEmbed, HttpEmbed }
import java.util.ArrayList

import org.apache.http.NameValuePair
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.client.methods.{ HttpGet, HttpPost }
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.protocol.HTTP
import org.apache.http.params.BasicHttpParams
import org.apache.http.message.BasicNameValuePair

object SPHP {
  val resin = new ResinEmbed
  private var _host = "http://0.0.0.0"
  private var _port = 8080

  def init(host: String, port: Int, rootDirectory: String) {
    _host = host 
    _port = port
    resin.addWebApp(new WebAppEmbed("/", rootDirectory))
    resin.addPort(new HttpEmbed(_port))
    resin.start
  } 

  def request(method: String, path: String, parameters: Map[String, String] = Map()): String = {
    val httpClient = new DefaultHttpClient
    val responseHandler = new BasicResponseHandler
    var result = ""
    var uri = "%s:%d%s" format (_host, _port, path)

    val params = new ArrayList[NameValuePair]
    for((k, v) <- parameters) {
      params.add(new BasicNameValuePair(k, v))
    }

    if (method == "get") {
      uri += (if (uri.contains("?")) "&" else "?") + URLEncodedUtils.format(params, HTTP.UTF_8)
      result = httpClient.execute(new HttpGet(uri), responseHandler)

    } else {
      val req = new HttpPost(uri)
      req.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8))
      result = httpClient.execute(req, responseHandler)
    }

    httpClient.getConnectionManager.shutdown

    return result
  }
}

// vim: set ts=2 sw=2 et:
