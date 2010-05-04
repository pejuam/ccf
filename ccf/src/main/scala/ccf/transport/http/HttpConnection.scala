package ccf.transport.http

import java.io.IOException
import java.net.URL

import org.apache.http.params.HttpConnectionParams

import ccf.transport.json.{JsonFormatter, JsonParser}

import dispatch.{Request => HttpRequest, _}
import Http._

import scala.collection.immutable.TreeMap

class HttpConnection(url: URL, timeoutMillis: Int) extends Connection {
  private val formatter = JsonFormatter
  private val parser = JsonParser
  private val http = new Http
  init
  def send(request: Request): Option[Response] = {
    val req = requestUrl(request).POST << formatter.format(request)
    http(req >- { parser.parse(_) })
  }
  private def requestUrl(request: Request) = url.toString / request.header("type").getOrElse(requestTypeMissing)
  private def requestTypeMissing = throw new InvalidRequestException("Request header \"type\" missing")
  private def init = {
    HttpConnectionParams.setConnectionTimeout(httpClientParams, timeoutMillis)
    HttpConnectionParams.setSoTimeout(httpClientParams, timeoutMillis)
  }
  private def httpClient = http.client
  private def httpClientParams = httpClient.getParams
}
