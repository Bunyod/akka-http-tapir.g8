package $package$
package http
package template

import akka.http.scaladsl.server
import akka.http.scaladsl.server.{Directives, RouteConcatenation}
import cats.implicits._
import domain.template.{$className;format="Camel"$Service, Tweet}
import http.auth.Authenticable
import util.cfg.Configurable
import util.json.FailFastCirceSupport
import com.typesafe.scalalogging.LazyLogging
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class $className;format="Camel"$Routes(svc: $className;format="Camel"$Service)(implicit ec: ExecutionContext)
    extends Directives
    with Configurable
    with Authenticable
    with FailFastCirceSupport
    with RouteConcatenation
    with LazyLogging {

  import akka.http.scaladsl.server.{Directives => directive}

  val route = getTweets ~ add

  def getTweets: server.Route = directive.get {
    requireAuth { _: ApiClientIdentity =>
      path("tweet" / Segment) { username =>
        parameters('limit.as[Int]) { limit =>
          complete(
            svc.tweets(username, limit).fold(err => err.toHttpResponse, res => res.asJson.toHttpResponse)
          )
        }
      }
    }
  }

  def add: server.Route = directive.post {
    path("tweet") {
      entity(as[Tweet]) { twt =>
        complete(
          svc
            .tweets(twt.text, 12)
            .fold(err => err.toHttpResponse, res => res.asJson.toHttpResponse)
        )
      }
    }
  }

}
