package $package$

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, ExceptionHandler}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.typesafe.scalalogging.LazyLogging
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.time.{Minute, Seconds, Span}

abstract class UnitSpec
    extends AsyncWordSpec
    with Matchers
    with ScalatestRouteTest
    with MockitoSugar
    with AsyncMockFactory
    with ScalaFutures
    with LazyLogging {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(1, Minute)),
    interval = scaled(Span(15, Seconds))
  )

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler { case e =>
      Directives.complete(HttpResponse(StatusCodes.InternalServerError, entity = "Internal Server Error"))
    }


}
