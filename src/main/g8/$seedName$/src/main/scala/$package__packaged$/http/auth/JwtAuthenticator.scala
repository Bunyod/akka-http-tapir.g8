package $package$.http.auth

import akka.http.scaladsl.server.directives.Credentials
import authentikat.jwt.JsonWebToken
import $package$.http.ApiClientIdentity

import scala.concurrent.Future
import scala.util.{Failure, Success}

class JwtAuthenticator(secret: String) {

    /**
      * Returning None means that the authentication failed.
      *
      * @param credentials
      * @return
      */
    def basicJwtAuthenticator(credentials: Credentials): Option[ApiClientIdentity] =
      credentials match {
        case Credentials.Provided(token) =>
          if (!JsonWebToken.validate(token, secret)) {
            None
          } else {
            token match {
              case JsonWebToken(header, tokenClaims, _) =>
                /**
                  * Currently, the library does not accept "none" as algorithm. But, according to the JWT specification,
                  * "none" is a valid algorithm - which is a great security issue, because then anybody can create a
                  * token that is not signed and therefore will be accepted as valid. We should not accept it.
                  *
                  * To future proof, we should have this requirement here.
                  *
                  * See: https://github.com/jasongoodwin/authentikat-jwt/issues/31
                  */
                require(!header.algorithm.getOrElse("none").equals("none"), "We do not accept 'none' as algorithm")

                tokenClaims.asSimpleMap match {
                  case Failure(_) => None
                  case Success(claims) =>
                    Some(
                      ApiClientIdentity(
                        clientId = claims.getOrElse("clientId", "unknown")
                      )
                    )
                }
              case _ => None
            }
          }
        case _ => None
      }

    def basicJwtAuthenticatorAsync(credentials: Credentials): Future[Option[ApiClientIdentity]] =
      Future.successful(basicJwtAuthenticator(credentials))
  }
