package $package$

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}

class StarterIt extends IntegrationTest {

  "Service" should {
    "bind on port successfully and return 10 tweets" in {
      val authorization = Authorization(OAuth2BearerToken("test"))

      (for {
        serverBinding <- Starter.startApplication()
        result <- Http().singleRequest(
                   HttpRequest(
                     uri = "http://localhost:8082/api/v1/tweet/funnytweets?limit=10",
                     headers = List(authorization)
                   )
                 )
        _ <- serverBinding.unbind()
      } yield {
        result.status.intValue() shouldBe 200
        result.status shouldEqual StatusCodes.OK
      }).futureValue
    }
  }
}
