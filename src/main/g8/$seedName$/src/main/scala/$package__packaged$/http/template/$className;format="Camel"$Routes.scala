package $package$
package http
package template

import akka.http.scaladsl.server.{Directives, Route, RouteConcatenation}
import domain.template.{$className;format="Camel"$Service, Tweet}
import http.auth.Authenticable
import util.cfg.Configurable
import util.json.FailFastCirceSupport
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.{ExecutionContext, Future}

import tapir.server.akkahttp._
import io.circe.generic.auto._
import tapir.json.circe._
import tapir.{endpoint, jsonBody, Endpoint, _}

class $className;format="Camel"$Routes(svc: $className;format="Camel"$Service)(implicit ec: ExecutionContext)
  extends Configurable with Authenticable with FailFastCirceSupport with LazyLogging with RouteConcatenation {

  import akka.http.scaladsl.server.{Directives => directive}

  private val basePath = "api"
  private val version = "v1"

  val route = getTweetsRoute ~ addTweetRoute

  private def getTweets(username: String, limit: Int): Future[Either[Error, Seq[String]]] =
    svc.tweets(username, limit).value

  val getTweetsEndpoint: Endpoint[(String, String, Int), Error, Seq[String], Nothing] =
    endpoint.get
      .in(auth.bearer)
      .in(basePath / version / "tweet")
      .in(path[String]("username"))
      .in(query[Int]("limit"))
      .out(jsonBody[Seq[String]])
      .errorOut(jsonBody[Error])

  def getTweetsRoute: Route = directive.get {
    requireAuth { _: ApiClientIdentity =>
      getTweetsEndpoint.toRoute {
        case (_, username, limit) =>
        getTweets(username, limit)
      }
    }
  }

  val addTweetEndpoint: Endpoint[(String, Tweet), Error, Seq[String], Nothing] =
    endpoint.post
      .in(auth.bearer)
      .in(basePath / version / "tweet")
      .in(jsonBody[Tweet])
      .out(jsonBody[Seq[String]])
      .errorOut(jsonBody[Error])

  def addTweetRoute: Route = directive.post {
    requireAuth { _: ApiClientIdentity =>
      addTweetEndpoint.toRoute {
        case (_, twt) =>
        svc.tweets(twt.text, 12).value: Future[Either[Error, Seq[String]]]
      }
    }
  }

}
