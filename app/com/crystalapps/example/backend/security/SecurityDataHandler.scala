package com.crystalapps.example.backend.security

import com.crystalapps.example.backend.common.CommonClass
import com.crystalapps.example.backend.services.UserService
import com.crystalapps.example.backend.services.security.OAuthAccessTokenService
import com.crystalapps.example.backend.common.{Common, CommonClass}
import com.crystalapps.example.backend.database.models.SecurityModels.OAuthAuthorizationCodeVo
import com.crystalapps.example.backend.database.models.UserModels.UserModel
import com.crystalapps.example.backend.services.UserService
import com.crystalapps.example.backend.services.dto.LoginServiceDto.LoginDto
import com.crystalapps.example.backend.services.security.{OAuthAccessTokenService, OAuthAuthorizationCodeService, OAuthClientService}
import javax.inject.Inject
import scalaoauth2.provider._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/13.
  * #F-SDH
  */
class SecurityDataHandler @Inject()(userService: UserService,
                                    clientService: OAuthClientService,
                                    authorizationCodeService: OAuthAuthorizationCodeService,
                                    accessTokenService: OAuthAccessTokenService)
                                   (implicit executionContext: ExecutionContext)
    extends DataHandler[UserModel] with CommonClass {

  import com.crystalapps.example.backend.common.OptionHelper._

  /** #F-SDH-01. */
  override def validateClient(maybeCredential: Option[ClientCredential], request: AuthorizationRequest): Future[Boolean] = {
    logFuncK("validateClient", "F-SDH-01", maybeCredential, request)(
      (for {
        credential <- maybeCredential
      } yield {
        request.grantType match {
          case OAuthGrantType.CLIENT_CREDENTIALS =>
            clientService.validate(credential.clientId, credential.clientSecret, Some(request.grantType))
          case OAuthGrantType.AUTHORIZATION_CODE =>
            clientService.validate(credential.clientId, credential.clientSecret, Some(request.grantType))
          case OAuthGrantType.REFRESH_TOKEN =>
            clientService.validate(credential.clientId, credential.clientSecret, None)
          case OAuthGrantType.IMPLICIT =>
            clientService.validate(credential.clientId, credential.clientSecret, Some(request.grantType))
          case OAuthGrantType.PASSWORD =>
            Future.successful(true)
          case _ =>
            throw new UnsupportedGrantType(s"${request.grantType} is not supported")
        }
      }).getOrElse(Future.failed(new InvalidClient()))
    )
  }

  /** #F-SDH-02. */
  override def findUser(maybeCredential: Option[ClientCredential], request: AuthorizationRequest): Future[Option[UserModel]] = {
    logFuncK("findUser", "F-SDH-02", maybeCredential, request)(
      request match {
        case request: ClientCredentialsRequest =>
          maybeCredential.map(credential =>
            clientService.findUserByCredentials(credential.clientId, credential.clientSecret, request.grantType)
          ).getOrElse(throw new InvalidClient())
        case request: AuthorizationCodeRequest =>
          maybeCredential.map(credential =>
            clientService.findUserByCredentials(credential.clientId, credential.clientSecret, request.grantType)
          ).getOrElse(throw new InvalidClient())
        case _: RefreshTokenRequest =>
          maybeCredential.map(credential =>
            clientService.findUserByCredentials(credential.clientId, credential.clientSecret, OAuthGrantType.AUTHORIZATION_CODE)
          ).getOrElse(throw new InvalidClient())
        case request: ImplicitRequest =>
          maybeCredential.map(credential =>
            clientService.findUserByCredentials(credential.clientId, credential.clientSecret, request.grantType)
          ).getOrElse(throw new InvalidClient())
        case request: PasswordRequest =>
          userService.loginAuth(
            request.username,
            request.password
          )
        case _ =>
          Future.successful(None)
      }
    )
    /*for {
      credential <- maybeCredential
      account <- clientService.findUserByCredentials(credential.clientId, credential.clientSecret)
    } yield account*/
  }

  /** #F-SDH-03. */
  override def createAccessToken(authInfo: AuthInfo[UserModel]): Future[AccessToken] = {
    logFuncK("createAccessToken", "F-SDH-03", authInfo)({
      val user = authInfo.user
      val requestScopes = authInfo.scope.ifDefined(_.split(",").map(_.trim).toSeq, _ => Seq.empty[String])
      clientService.findByClientId(authInfo.clientId.getOrElse("")).flatMap(result => {
        val client = result.getOrElse(throw new InvalidClient())
        val scopeVerified = requestScopes.forall(client.scope.contains)
        if (!scopeVerified) throw new InvalidScope()
        accessTokenService.createAccessTokenByUserAndClient(user, client).map(_.getOrElse(throw new InvalidRequest()))
      }).map(SecurityCommon.toAccessToken)
    })
  }

  /** #F-SDH-04. */
  override def getStoredAccessToken(authInfo: AuthInfo[UserModel]): Future[Option[AccessToken]] = {
    logFuncK("getStoredAccessToken", "F-SDH-04", authInfo)(
      accessTokenService.findByAuthInfo(authInfo).map(result => {
        result.map(SecurityCommon.toAccessToken)
      })
    )
  }

  /** #F-SDH-05. */
  override def refreshAccessToken(authInfo: AuthInfo[UserModel], refreshToken: String): Future[AccessToken] = {
    logFuncK("refreshAccessToken", "F-SDH-05", authInfo, refreshToken)({
      val user = authInfo.user
      clientService.findByClientId(authInfo.clientId.getOrElse("")).flatMap(result => {
        val client = result.getOrElse(throw new InvalidClient())
        accessTokenService.refreshAccessTokenByUserAndClient(user, client).map(_.getOrElse(throw new InvalidRequest()))
      }).map(SecurityCommon.toAccessToken)
    })
  }

  /** #F-SDH-06. */
  override def findAuthInfoByCode(code: String): Future[Option[AuthInfo[UserModel]]] = {
    logFuncK("findAuthInfoByCode", "F-SDH-06", code)(
      authorizationCodeService.findByVoData(authCode = Some(code)).flatMap(
        _.map(authorization =>
          userService.findByVoData(id = Some(authorization.userId)).map(_.map(user =>
            AuthInfo(user,
              Some(Common.UUIDToString(authorization.clientId)),
              None,
              authorization.redirectUri)
          ))
        ).getOrElse(Future.failed(new InvalidRequest()))
      )
    )
  }

  /** #F-SDH-07. */
  override def deleteAuthCode(code: String): Future[Unit] = {
    logFuncK("deleteAuthCode", "F-SDH-07", code)(
      authorizationCodeService.remove(OAuthAuthorizationCodeVo(authCode = Some(code))).map(result => {
        println(result)
      })
    )
  }

  /** #F-SDH-08. */
  override def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[UserModel]]] = {
    logFuncK("findAuthInfoByRefreshToken", "F-SDH-08", refreshToken)(
      accessTokenService.findByVoData(refreshToken = Some(refreshToken)).flatMap(
        _.map(accessToken =>
          userService.findByVoData(id = Some(accessToken.userId)).map(_.map(user =>
            AuthInfo(user,
              Some(Common.UUIDToString(accessToken.clientId)),
              accessToken.getScopeStringOption,
              None)
          ))
        ).getOrElse(Future.failed(new InvalidToken()))
      )
    )
  }

  /** #F-SDH-09. */
  override def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[UserModel]]] = {
    logFuncK("findAuthInfoByAccessToken", "F-SDH-09", accessToken)(
      accessTokenService.findByVoData(accessToken = Some(accessToken.token)).flatMap(
        _.map(accessToken =>
          userService.findByVoData(id = Some(accessToken.userId)).map(_.map(user =>
            AuthInfo(user,
              Some(Common.UUIDToString(accessToken.userId)),
              accessToken.getScopeStringOption,
              None)
          ))
        ).getOrElse(Future.failed(new InvalidToken()))
      )
    )
  }

  /** #F-SDH-10. */
  override def findAccessToken(token: String): Future[Option[AccessToken]] = {
    logFuncK("findAccessToken", "F-SDH-10", token)(
      accessTokenService.findByVoData(accessToken = Some(token)).map(
        _.map(SecurityCommon.toAccessToken)
      )
    )
  }

}
