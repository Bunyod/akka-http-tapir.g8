package $package$
package http
package swagger

import java.util.Properties

class SwaggerRoutes(yml: String) {
  import akka.http.scaladsl.model.StatusCodes
  import akka.http.scaladsl.server.Directives._
  import akka.http.scaladsl.server.Route

  private val swaggerPath = "docs"
  val swaggerYml = "swagger.yml"

  private val redirectToIndex: Route =
    redirect("/docs/index.html?url=/docs/swagger.yml", StatusCodes.PermanentRedirect)

  private val swaggerVersion = {
    val p = new Properties()
    p.load(getClass.getResourceAsStream("/META-INF/maven/org.webjars/swagger-ui/pom.properties"))
    p.getProperty("version")
  }

  val routes: Route =
    pathPrefix(swaggerPath) {
      path("") {
        redirectToIndex
      } ~
      path(swaggerYml) {
        complete(yml)
      } ~
      getFromResourceDirectory("META-INF/resources/webjars/swagger-ui/swaggerVersion/")

      //FIXME use dollar sign before swaggerVersion
    }
}
