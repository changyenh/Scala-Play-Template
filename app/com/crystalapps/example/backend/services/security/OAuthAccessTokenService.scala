package com.crystalapps.example.backend.services.security

import java.util.UUID

import com.crystalapps.example.backend.database.dao.security.OAuthAccessTokenDao
import javax.inject.Inject
import com.crystalapps.example.backend.common.Common
import com.crystalapps.example.backend.database.dao.security.OAuthAccessTokenDao
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthAccessTokenModel, OAuthAccessTokenVo, OAuthClientModel}
import com.crystalapps.example.backend.database.models.UserModels.UserModel
import com.crystalapps.example.backend.security.SecurityCommon
import com.crystalapps.example.backend.services.ServiceTrait

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import scalaoauth2.provider.{AuthInfo, OAuthGrantType}

/**
  * Created by changyenh on 西暦18/05/14.
  * #S-OAATS
  */
class OAuthAccessTokenService @Inject()(dao: OAuthAccessTokenDao)
                                       (implicit executionContext: ExecutionContext)
    extends ServiceTrait {

  /** #S-OAATS-01. */
  def all: Future[Seq[OAuthAccessTokenModel]] = {
    logServiceK("all", "S-OAATS-01")(
      dao.all().map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OAATS-02. */
  def insert(model: OAuthAccessTokenModel): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("insert", "S-OAATS-02", model)(
      dao.insert(model)
    ).recover(optionRecover)
  }

  /** #S-OAATS-03. */
  def update(model: OAuthAccessTokenModel): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("update", "S-OAATS-03", model)(
      dao.update(model).map({
        case Some(1) => Option(model)
        case _ => Option.empty
      })
    ).recover(optionRecover)
  }

  /** #S-OAATS-04. */
  def find(vo: OAuthAccessTokenVo): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("find", "S-OAATS-04", vo)(
      dao.find(vo)
    ).recover(optionRecover)
  }

  /** #S-OAATS-05. */
  def findList(vo: OAuthAccessTokenVo): Future[Seq[OAuthAccessTokenModel]] = {
    logServiceK("findList", "S-OAATS-05", vo)(
      dao.findList(vo).map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OAATS-06. */
  def remove(vo: OAuthAccessTokenVo): Future[Option[Int]] = {
    logServiceK("remove", "S-OAATS-06", vo)(
      dao.remove(vo)
    ).recover(optionRecover)
  }

  /** #S-OAATS-07. */
  def createAccessTokenByUserAndClient(user: UserModel, client: OAuthClientModel): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("createAccessTokenByUserAndClient", "S-OAATS-07", user, client)({
      val accessToken = SecurityCommon.randomString(40)
      val refreshToken = client.grantType match {
        case OAuthGrantType.CLIENT_CREDENTIALS => None
        case OAuthGrantType.AUTHORIZATION_CODE => Some(SecurityCommon.generateToken)
        case OAuthGrantType.REFRESH_TOKEN => Some(SecurityCommon.generateToken)
        case _ => None
      }
      val createdAt = Common.getCurrentTimestamp
      val model = OAuthAccessTokenModel(
        0,
        user.id,
        client.clientId,
        accessToken,
        refreshToken,
        client.scope,
        createdAt
      )
      insert(model)
    }).recover(optionRecover)
  }

  /** #S-OAATS-08. */
  def deleteAccessTokenByUserAndClient(user: UserModel, client: OAuthClientModel): Future[Option[Int]] = {
    logServiceK("deleteAccessTokenByUserAndClient", "S-OAATS-08", user, client)(
      dao.remove(OAuthAccessTokenVo(
        userId = Some(user.id),
        clientId = Some(client.userId)
      ))
    ).recover(optionRecover)
  }

  /** #S-OAATS-09. */
  def refreshAccessTokenByUserAndClient(user: UserModel, client: OAuthClientModel): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("deleteAccessTokenByUserAndClient", "S-OAATS-08", user, client)({
      deleteAccessTokenByUserAndClient(user, client).onComplete {
        case Success(result) => info(result.getOrElse("-1"))
        case Failure(t) => error(t)
      }
      createAccessTokenByUserAndClient(user, client)
    }).recover(optionRecover)
  }

  /** #S-OAATS-10. */
  def findByAuthInfo(authInfo: AuthInfo[UserModel]): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("findByAuthInfo", "S-OAATS-10", authInfo)({
      val userId = Some(authInfo.user.id)
      val clientId = authInfo.clientId.map(Common.stringToUUID)
      val accessTokenVo = OAuthAccessTokenVo(userId = userId, clientId = clientId)
      find(accessTokenVo)
    }).recover(optionRecover)
  }

  /** #S-OAATS-11. */
  def findByVoData(userId: Option[UUID] = Option.empty,
                   clientId: Option[UUID] = Option.empty,
                   accessToken: Option[String] = Option.empty,
                   refreshToken: Option[String] = Option.empty): Future[Option[OAuthAccessTokenModel]] = {
    logServiceK("findByVoData", "S-OAATS-11", userId, clientId, accessToken, refreshToken)(
      find(OAuthAccessTokenVo(
        Option.empty, userId, clientId, accessToken, refreshToken
      ))
    ).recover(optionRecover)
  }

}
