package $package$
package domain
package spec

import tapir.docs.openapi._
import tapir.openapi.circe.yaml._

object ApiSpecification {

  val basePath = "api"

  val teplateEndoints = new TemplateEndpoints(basePath)

  val Yaml = List(
    teplateEndoints.getTweets,
    teplateEndoints.addTweet
  )
  .toOpenAPI("Template API", "1.0").toYaml // We can use here BuildInfo.version instead of hardcoded version
}
