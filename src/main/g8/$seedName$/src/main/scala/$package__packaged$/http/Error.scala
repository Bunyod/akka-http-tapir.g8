package $package$
package http

import akka.http.scaladsl.model.StatusCode

final case class Error(code: StatusCode, message: String)
