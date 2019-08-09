package $package$

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{handleExceptions, handleRejections}
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import domain.template
import repository.template.inmemmory
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.{ExecutionContext, Future}

object Starter
    extends App with RouteConcatenation
    with util.cfg.Configurable
    with util.http.ExceptionHandler
    with util.http.RejectionHandler
    with util.log.SentryLogging
    with LazyLogging {

  def startApplication(): Future[Http.ServerBinding] = {

    implicit val actorSystem: ActorSystem = ActorSystem()
    implicit val executor: ExecutionContext = actorSystem.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()(actorSystem)

    val repoTweet: inmemmory.$className;format="Camel"$Repository = new inmemmory.$className;format="Camel"$Repository()
    val svcTweet: template.$className;format="Camel"$Service = new template.$className;format="Camel"$Service(repoTweet)
    val rteTweet: http.template.$className;format="Camel"$Routes = new http.template.$className;format="Camel"$Routes(svcTweet)
    val tweets: Route = rteTweet.route

    val rteSwagger: http.swagger.SwaggerRoutes = new http.swagger.SwaggerRoutes()
    val swagger: Route = rteSwagger.route
    val routes: Route = handleRejections(rejectionHandler) {
      handleExceptions(exceptionHandler) {
        cors() {
          tweets ~ swagger
        }
      }
    }

    Http().bindAndHandle(routes, config.http.host, config.http.port)
  }

  startApplication()

}
