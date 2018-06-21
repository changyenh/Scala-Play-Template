package com.crystalapps.example.backend.services.security

import java.util.UUID

import com.crystalapps.example.backend.database.dao.security.OAuthClientDao
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthClientModel, OAuthClientVo}
import com.crystalapps.example.backend.services.ServiceTrait
import javax.inject.Inject
import com.crystalapps.example.backend.common.Common
import com.crystalapps.example.backend.database.dao.UserDao
import com.crystalapps.example.backend.database.dao.security.OAuthClientDao
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthClientModel, OAuthClientVo}
import com.crystalapps.example.backend.database.models.UserModels.{UserModel, UserVo}
import com.crystalapps.example.backend.services.ServiceTrait

import scala.concurrent.{ExecutionContext, Future}
import scalaoauth2.provider.InvalidClient

/**
  * Created by changyenh on 西暦18/05/14.
  * #S-OACS
  */
class OAuthClientService @Inject()(dao: OAuthClientDao,
                                   userDao: UserDao)
                                  (implicit executionContext: ExecutionContext)
    extends ServiceTrait {

  /** #S-OACS-01. */
  def all: Future[Seq[OAuthClientModel]] = {
    logServiceK("all", "S-OACS-01")(
      dao.all().map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OACS-02. */
  def insert(model: OAuthClientModel): Future[Option[OAuthClientModel]] = {
    logServiceK("insert", "S-OACS-02", model)(
      dao.insert(model)
    ).recover(optionRecover)
  }

  /** #S-OACS-03. */
  def update(model: OAuthClientModel): Future[Option[OAuthClientModel]] = {
    logServiceK("update", "S-OACS-03", model)(
      dao.update(model).map({
        case Some(1) => Option(model)
        case _ => Option.empty
      })
    ).recover(optionRecover)
  }

  /** #S-OACS-04. */
  def find(vo: OAuthClientVo): Future[Option[OAuthClientModel]] = {
    logServiceK("find", "S-OACS-04", vo)(
      dao.find(vo)
    ).recover(optionRecover)
  }

  /** #S-OACS-05. */
  def findList(vo: OAuthClientVo): Future[Seq[OAuthClientModel]] = {
    logServiceK("findList", "S-OAACS-05", vo)(
      dao.findList(vo).map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OACS-06. */
  def remove(vo: OAuthClientVo): Future[Option[Int]] = {
    logServiceK("remove", "S-OAACS-06", vo)(
      dao.remove(vo)
    ).recover(optionRecover)
  }

  /** #S-OACS-07. */
  def findByVoData(clientId: Option[UUID] = Option.empty,
                   clientSecret: Option[String] = Option.empty,
                   redirectUri: Option[String] = Option.empty,
                   grantType: Option[String] = Option.empty,
                   userId: Option[UUID] = Option.empty): Future[Option[OAuthClientModel]] = {
    logServiceK("findByVoData", "S-OACS-07", clientId, clientSecret, redirectUri, grantType)(
      dao.find(OAuthClientVo(
        clientId, clientSecret, redirectUri, grantType, userId
      ))
    ).recover(optionRecover)
  }

  /** #S-OACS-08. */
  def validate(clientId: String, clientSecret: Option[String], grantType: Option[String]): Future[Boolean] = {
    logServiceK("validate", "S-OACS-08", clientId, clientSecret)({
      val uuid = Some(Common.stringToUUID(clientId))
      findByVoData(uuid, clientSecret, grantType = grantType).map(_.isDefined)
    })
  }

  /** #S-OACS-09. */
  def findByClientId(clientId: String): Future[Option[OAuthClientModel]] = {
    logServiceK("findByClientId", "S-OACS-09", clientId)({
      val uuid = Some(Common.stringToUUID(clientId))
      findByVoData(uuid)
    }).recover(optionRecover)
  }

  /** #S-OACS-10. */
  def findUserByCredentials(clientId: String, clientSecret: Option[String], grantType: String): Future[Option[UserModel]] = {
    logServiceK("findUserByCredentials", "S-OACS-10", clientId, clientSecret)({
      val uuid = Some(Common.stringToUUID(clientId))
      findByVoData(uuid, clientSecret, grantType = Some(grantType)).flatMap(_.map(client =>
        userDao.find(UserVo().setId(client.userId)).recover(optionRecover)
      ).getOrElse(Future.failed(new InvalidClient())))
    })
  }

}
