package $package$
package http

import akka.http.scaladsl.model.StatusCode
import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}

final case class Error(code: StatusCode, message: String)

object Error {
  implicit val encodeStatusCode: Encoder[StatusCode] =
    Encoder.encodeInt.contramap[StatusCode](_.intValue)

  implicit val decodeStatusCode: Decoder[StatusCode] =
    Decoder.decodeInt.emap(
      int =>
        try {
          Right(StatusCode.int2StatusCode(int))
        } catch {
          case _: RuntimeException => Left("StatusCode")
        }
    )

  // private [http] def apply(code: StatusCode, message: String): Error = new Error(code, message)

}
