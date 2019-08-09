package $package$
package http
package swagger


import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server
import akka.http.scaladsl.server.{Directives, RouteConcatenation}
import $package$.domain.swagger.SwaggerService
import $package$.util.cfg.Configurable
import $package$.util.json.FailFastCirceSupport
import com.typesafe.scalalogging.LazyLogging
import javax.ws.rs.Path
import scala.util.{Failure, Success, Try}

@Path("/docs")
class SwaggerRoutes
    extends Directives
    with Configurable
    with FailFastCirceSupport
    with RouteConcatenation
    with LazyLogging {

  def route: server.Route = {
    val docTry = Try {
      SwaggerService.generateSwaggerJson

      domain.swagger.SwaggerService.routes ~
        path("docs") {
          getFromResource("swagger/index.html")
        } ~
        getFromResourceDirectory("swagger")
    }

    docTry match {
      case Success(value) => value
      case Failure(exception) =>
        logger.error("Swagger is disabled, because it can't generate the documentation successfully", exception)
        path("docs") {
          complete(HttpResponse(StatusCodes.InternalServerError, entity = "Swagger can't generate the documentation"))
        }
    }
  }

}