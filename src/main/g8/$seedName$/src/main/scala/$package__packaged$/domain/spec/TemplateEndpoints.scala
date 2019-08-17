package $package$
package domain
package spec

import http.Error
import domain.template.Tweet
import io.circe.generic.auto._
import tapir.json.circe._
import tapir.{Endpoint, endpoint, jsonBody, _}

class TemplateEndpoints(basePath: String) {

  val versionKey = "v1"

  val getTweets: Endpoint[(String, String, Int), Error, List[String], Nothing] =
    endpoint.get
      .in(auth.bearer)
      .in(basePath / versionKey / "tweet")
      .in(path[String]("username"))
      .in(query[Int]("limit"))
      .out(jsonBody[List[String]])
      .errorOut(jsonBody[Error])

  val addTweet: Endpoint[( String, Tweet), Error, List[String], Nothing] =
    endpoint.post
      .in(auth.bearer)
      .in(basePath / versionKey / "tweet")
      .in(
        jsonBody[Tweet]
          .example(
            Tweet(text = "Hello Twitter!")
          )
      )
      .out(jsonBody[List[String]])
      .errorOut(jsonBody[Error])
}
