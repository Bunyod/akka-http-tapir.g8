package $package$.http

/**
 * Used for authentication of the API client.
 */
final case class ApiClientIdentity(
  /**
   * This will help us identify who is accessing our API for authorization.
   * We can also enforce that the User-Agent header is required in the requests and that it should
   * always contain the client id there.
   *
   * E.g.:
   * * Valid case => clientId is pink-elephant: User-Agent is "pink-elephant worker v3.0.1-bla"
   * * Invalid case => clientId is pink-elephant: User-Agent is "pinkElephant v1.0.1-bla"
   * * Invalid case => clientId is pink-elephant: User-Agent is "orange-caterpillar v1.0.1-bla"
   *
   * This forces the client application to set a header that makes sense. Also, this should help to
   * avoid sharing of tokens, because if your project is red-apple, it will feel wrong to set in the
   * User-Agent the red-apple name.
   */
  clientId: String
)
