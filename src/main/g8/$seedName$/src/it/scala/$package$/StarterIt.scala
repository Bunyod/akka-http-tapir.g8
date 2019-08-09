package $package$

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}

class StarterIt extends IntegrationTest {

  "Service" should {
    "bind on port successfully and return 10 tweets" in {
      (for {
        serverBinding <- Starter.startApplication()
        result <- Http().singleRequest(HttpRequest(uri = "http://localhost:8082/tweet/realDonaldTrump?limit=10"))
        _ <- serverBinding.unbind()
      } yield {
        result.status.intValue() shouldBe 200
        result.status shouldEqual StatusCodes.OK
      }).futureValue
    }
  }

}
