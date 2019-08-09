package $package$

import akka.http.scaladsl.model._
import io.circe.syntax._
import io.circe.{Json, Printer}

package object http {

  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  implicit class ErrorResponseWrapper(response: Error) {
    def toHttpResponse: HttpResponse = HttpResponse(
      response.code,
      entity = HttpEntity(ContentTypes.`application/json`, response.asJson.toString())
    )
  }

  import StatusCodes._
  implicit class HttpResponseWrapper(response: Json) {
    def toHttpResponse: HttpResponse = HttpResponse(
      OK,
      entity = HttpEntity(ContentTypes.`application/json`, printer.pretty(response))
    )
  }

}
