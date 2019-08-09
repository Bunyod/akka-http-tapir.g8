package $package$
package domain
package template

import akka.actor.ActorSystem
import repository.template.inmemmory.$className;format="Camel"$Repository
import scala.concurrent.Future

class $className;format="Camel"$ServiceSpec extends UnitSpec with StubData {

  val actsys: ActorSystem = system

  val trim: $className;format="Camel"$Repository = stub[$className;format="Camel"$Repository ]
  val svc: $className;format="Camel"$Service = new $className;format="Camel"$Service(trim)

  "The Shout service" when {
    "initiated for getting last N tweets" should {
      "response with a 'Seq[Tweet]'" in {

        val limit = 3
        val trimMock: Future[Seq[Tweet]] = Future(tweets(limit))
        (trim.searchByUserName _).when(userName, limit).returns(trimMock)

        whenReady(svc.tweets(userName, limit).value) {
          case Right(res) => res.size shouldEqual limit
          case Left(err) => failTest(err.message)
        }
      }

      "response with 10 tweets" in {
        val limit = 10
        val trimMock: Future[Seq[Tweet]] = Future(tweets(limit))
        (trim.searchByUserName _).when(userName, limit).returns(trimMock)

        svc.tweets(userName, limit).value.futureValue match {
          case Right(res) => res.size shouldEqual limit
          case Left(err) => failTest(err.message)
        }
      }

      "response with an error stating the api is not available" in {
        val limit = 10
        val trimMock: Future[Seq[Tweet]] = Future.failed(new Exception("Wrong credentials..."))
        (trim.searchByUserName _).when(userName, limit).returns(trimMock)

        svc.tweets(userName, limit).value.futureValue match {
          case Left(err) => err.message shouldEqual "Twitter api is not available"
          case Right(_) => failTest("Twitter connection error expected")
        }
      }

    }
  }

}
