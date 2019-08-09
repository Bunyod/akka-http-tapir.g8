package $package$.util.jwt

import authentikat.jwt._
import $package$.http.ApiClientIdentity
import com.typesafe.scalalogging.LazyLogging

/**
  * Run this to generate tokens for an API client.
  */
object JWTGenerator extends App with LazyLogging {
  /**
    * Change this to generate a token for a client
    */
  val clientId = "developer" // Used for identifying who is using our API
  val jwtSecret = "tyQPjw7tVfAzLBSdAnDVjLXjms6DqsoBIQoaRLRJ" // Used for signing the token
  val jwtSigningAlgorithm = "HS256" // Used for signing the token

  /**
    * Generates a signed JWT token string
    *
    * @param apiClientIdentity - To whom are we generating the token
    * @return
    */
  def generateToken(apiClientIdentity: ApiClientIdentity): String = {
    val claims = JwtClaimsSetMap(Map("clientId" -> apiClientIdentity.clientId)
    JsonWebToken.apply(JwtHeader(jwtSigningAlgorithm), claims, jwtSecret)
  }

  val clientIdentity = ApiClientIdentity(clientId)
  val token = generateToken(clientIdentity)

  logger.info(s"""
                 |
     |JWTAuthenticator: Token generator
                 |
                 |\tClient: clientIdentity
                 |\tJWT Token: token
                 |
    """.stripMargin)

  //TODO add dollar sign before clientIdentity and token
}
