package com.crystalapps.example.backend.security.grant

import com.crystalapps.example.backend.common.FutureRecover
import com.crystalapps.example.backend.common.{Common, FutureRecover}
import com.crystalapps.example.backend.database.dao.UserDao
import com.crystalapps.example.backend.database.dao.security.OAuthClientDao
import com.crystalapps.example.backend.database.models.UserModels.UserModel
import com.crystalapps.example.backend.services.security.OAuthClientService
import javax.inject.Inject
import scalaoauth2.provider._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/16.
  */
class MyPasswordGrant @Inject()() extends Password with FutureRecover {

  override def clientCredentialRequired = false

  /*def handleUserRequest(maybeValidatedClientCred: Option[ClientCredential],
                        request: AuthorizationRequest,
                        handler: AuthorizationHandler[UserModel])
                       (implicit ctx: ExecutionContext): Future[GrantHandlerResult[UserModel]] = {
    /**
      * Given that client credentials may be optional, if they are required, they must be fully validated before
      * further processing.
      */
    if (clientCredentialRequired && maybeValidatedClientCred.isEmpty) {
      throw new InvalidRequest("Client credential is required")
    } else {
      val passwordRequest = PasswordRequest(request)
      handler.findUser(maybeValidatedClientCred, passwordRequest).flatMap { maybeUser =>
        val user = maybeUser.getOrElse(throw new InvalidGrant("username or password is incorrect"))
        val scope = passwordRequest.scope
        val authInfoFuture = for {
          clientCredential <- clientService.findByVoData(
            Some(user.id),
            grantType = Some(OAuthGrantType.PASSWORD)
          ).recover(optionRecover)
        } yield AuthInfo[UserModel](
          user,
          clientCredential.map(client => Common.UUIDToString(client.clientId)),
          scope,
          None
        )
        authInfoFuture.flatMap(authInfo => issueAccessToken(handler, authInfo))
      }
    }
  }*/
}
