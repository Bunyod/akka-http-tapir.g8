package $package$

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.typesafe.scalalogging.LazyLogging
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

abstract class IntegrationTest
    extends AsyncWordSpec
    with Matchers
    with ScalatestRouteTest
    with BeforeAndAfter
    with BeforeAndAfterAll
    with ScalaFutures
    with LazyLogging {

  implicit val patience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  override def beforeAll(): Unit = {
    logger.info("Starting application")
  }

  override def afterAll(): Unit = {
    logger.info("Stopping application...")
  }

}