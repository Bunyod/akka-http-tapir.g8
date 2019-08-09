package $package$
package util
package cfg


/** The application configuration
  *
  * @param http The Http settings of the current application
  * @param auth The Auth configurations of the current application
  * @param swagger The Swagger configurations of the current application
  */
final case class Config private[cfg] (
  http: HttpCfg,
  auth: JwtCfg,
  swagger: SwaggerCfg
)

/** The Http configuration of the current application
  *
  * @param host The Host of the current application
  * @param port The Port to bind the current application to.
  */
final case class HttpCfg private[cfg] (
  host: String,
  port: Int
)

/** The JWT configuration of the current application
  *
  * @param secret The secret
  * @param realm JWT provider which allows us to integrate with an existing authentication system
  */
final case class JwtCfg private[cfg] (
  realm: String,
  secret: String
)

/** The Swagger configuration of the current application
  *
  * @param host The Host of the swagger application
  */
final case class SwaggerCfg private[cfg] (host: String)
