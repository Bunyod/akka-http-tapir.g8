package $package$
package http
package auth

import akka.http.scaladsl.server.Directives.authenticateOAuth2Async
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.AuthenticationDirective
import $package$.util.cfg.Configurable

trait Authenticable { self: Configurable =>
  val requireAuth: AuthenticationDirective[ApiClientIdentity] = {

    if (!sys.env.contains("ENVIRONMENT")) {
      // Running locally
      new MockedAuthenticator
    } else {
      val jwtRealm: String = config.auth.realm
      val jwtSecret: String = config.auth.secret
      val jwtAuthenticator: JwtAuthenticator = new JwtAuthenticator(secret = jwtSecret)
      authenticateOAuth2Async(jwtRealm, jwtAuthenticator.basicJwtAuthenticatorAsync)
    }
  }
}

class MockedAuthenticator extends AuthenticationDirective[ApiClientIdentity] {

  override def tapply(f: Tuple1[ApiClientIdentity] => Route): Route = {
    // Fake client identity
    val identity = ApiClientIdentity(
      clientId = "local-developer"
    )
    f(Tuple1(identity))
  }
}
