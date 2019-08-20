package $package$
package http
package template

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import akka.http.scaladsl.server.Directives._
import cats.data._
import cats.implicits._
import domain.template.$className;format="Camel"$Service
import http._
import util.http.{ExceptionHandler, RejectionHandler}
import com.typesafe.scalalogging.LazyLogging
import org.scalamock.scalatest.AsyncMockFactory

import scala.concurrent.Future

class $className;format="Camel"$RoutesSpec extends UnitSpec with AsyncMockFactory {

  "The shout route" when {
    "called with 'GET /tweet/'" should {
      "return with a HTTP status code of 200 and a JSON 'Seq[String]' response" in {
        val ctx = new Context with StubData
        import ctx._

        val mock: svc.Response[Seq[String]] = EitherT(
          Future(tweets.asRight[Error])
        )

        (svc.tweets _).when("username", 10).returns(mock)
        val authorization = OAuth2BearerToken("test")

        Get("/api/v1/tweet/username?limit=10").addCredentials(authorization) ~> rte ~> check {
          status.intValue() shouldBe 200
          status shouldEqual StatusCodes.OK
        }
      }

      "return BadRequest for wrong field name" in {
        val ctx = new Context with StubData
        import ctx._

        val authorization = OAuth2BearerToken("test")

        Get("/api/v1/tweet/username?limi=10").addCredentials(authorization) ~> rte ~> check {
          status shouldEqual StatusCodes.BadRequest
        }
      }

      "return with a HTTP status code of 400 and a JSON 'Error' response" is pendingUntilFixed {
        val ctx = new Context with StubData
        import ctx._

        val mock: svc.Response[Seq[String]] = EitherT(
          Future(
            Error(StatusCodes.InternalServerError.intValue, "Error occurred during getting tweets").asLeft[Seq[String]]
          )
        )
        val authorization = OAuth2BearerToken("test")

        (svc.tweets _).when("username", 10).returns(mock)

        Get("/api/v1/tweet/username?limit=10").addCredentials(authorization) ~> rte ~> check {
          status shouldEqual StatusCodes.InternalServerError
          contentType shouldEqual ContentTypes.`application/json`
        }
      }
    }
  }

  trait Context extends LazyLogging with ExceptionHandler with RejectionHandler {

    val actsys: ActorSystem = system

    val svc: $className;format="Camel"$Service = stub[$className;format="Camel"$Service]
    val rte = handleExceptions(exceptionHandler) {
      new template.$className;format="Camel"$Routes(svc).route
    }
  }

}
