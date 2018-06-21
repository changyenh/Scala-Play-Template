package com.crystalapps.example.backend.security

import com.crystalapps.example.backend.security.grant.MyPasswordGrant
import scalaoauth2.provider._

/**
  * Created by changyenh on 西暦18/05/13.
  */
class MyTokenEndpoint extends TokenEndpoint {

  /**
    * https://auth0.com/docs/api-auth/which-oauth-flow-to-use#is-the-application-a-native-app-or-a-spa-
    * AUTHORIZATION_CODE => For server to server with refresh token. Or 3rd party native apps server(PKCE).
    * REFRESH_TOKEN => For server client to refresh access token by refresh token.
    * CLIENT_CREDENTIALS => For server side apps. Or if you have two native apps running with the same user.
    * IMPLICIT => For public single web apps. User login by their own and returns a access token to apps.
    * PASSWORD => For own web apps or native apps.
    */

  /**
    * 1. Should the redirect for authorization grant performed by server or user-agent.
    * 2. Where should I store the access token in web.
    */

  override val handlers = Map(
    OAuthGrantType.AUTHORIZATION_CODE -> new AuthorizationCode(),
    OAuthGrantType.REFRESH_TOKEN -> new RefreshToken(),
    OAuthGrantType.CLIENT_CREDENTIALS -> new ClientCredentials(),
    OAuthGrantType.IMPLICIT -> new Implicit(),
    OAuthGrantType.PASSWORD -> new Password()
  )

  /*
    code verifier -> Random URL-safe string with a minimum length of 43 characters.
    code challenge -> Base64 URL-encoded SHA-256 hash of the code verifier.

    Client generate "code verifier" and "code challenge"
    Authorize with "code challenge",
    Request token with "code verifier".

    Server verifies the "code verifier" by hashing it using the same hashing way and match it to the "code challenge".
    PKCE doesn't send the client credentials on requesting the access token.

    -Authorization Code Grant
    3rd party apps authorize through my server by the PKCE Authorization Code Grant.
    3rd party apps request client credentials by sending the "code verifier" to my server.
    3rd party apps send client credentials to 3rd party server.
    3rd party server reqeust token from my server with client credentials.

    -Client Credential Grant
    3rd party register from me and gets client credentials.
    3rd party server request token from my server with client credentials.

    -Implicit Grant
    public clients(untrusted such as my own native apps without login) request token from my server with a fixed client credentials.

    -Resource Owner Password Credentials Grant
    my own apps request token and login by sending a username and password to my server.

    -About storing user password on devices
    Should user a public key gained from the server and encrypt it before storing in your app.
    You could also send a backup of the password to the server.
   */

}
