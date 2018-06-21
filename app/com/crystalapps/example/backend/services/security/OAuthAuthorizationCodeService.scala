package com.crystalapps.example.backend.services.security

import java.util.UUID

import com.crystalapps.example.backend.database.dao.security.OAuthAuthorizationCodeDao
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthAuthorizationCodeModel, OAuthAuthorizationCodeVo}
import com.crystalapps.example.backend.services.ServiceTrait
import javax.inject.Inject
import com.crystalapps.example.backend.database.dao.security.OAuthAuthorizationCodeDao
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthAuthorizationCodeModel, OAuthAuthorizationCodeVo}
import com.crystalapps.example.backend.services.ServiceTrait

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/14.
  * #S-OAACS
  */
class OAuthAuthorizationCodeService @Inject()(dao: OAuthAuthorizationCodeDao)(implicit executionContext: ExecutionContext)
    extends ServiceTrait {

  /** #S-OAACS-01. */
  def all: Future[Seq[OAuthAuthorizationCodeModel]] = {
    logServiceK("all", "S-OAACS-01")(
      dao.all().map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OAACS-02. */
  def insert(model: OAuthAuthorizationCodeModel): Future[Option[OAuthAuthorizationCodeModel]] = {
    logServiceK("insert", "S-OAACS-02", model)(
      dao.insert(model)
    ).recover(optionRecover)
  }

  /** #S-OAACS-03. */
  def update(model: OAuthAuthorizationCodeModel): Future[Option[OAuthAuthorizationCodeModel]] = {
    logServiceK("update", "S-OAACS-03", model)(
      dao.update(model).map({
        case Some(1) => Option(model)
        case _ => Option.empty
      })
    ).recover(optionRecover)
  }

  /** #S-OAACS-04. */
  def find(vo: OAuthAuthorizationCodeVo): Future[Option[OAuthAuthorizationCodeModel]] = {
    logServiceK("find", "S-OAACS-04", vo)(
      dao.find(vo)
    ).recover(optionRecover)
  }

  /** #S-OAACS-05. */
  def findList(vo: OAuthAuthorizationCodeVo): Future[Seq[OAuthAuthorizationCodeModel]] = {
    logServiceK("findList", "S-OAACS-05", vo)(
      dao.findList(vo).map(_.get)
    ).recover(seqRecover)
  }

  /** #S-OAACS-06. */
  def remove(vo: OAuthAuthorizationCodeVo): Future[Option[Int]] = {
    logServiceK("remove", "S-OAACS-06", vo)(
      dao.remove(vo)
    ).recover(optionRecover)
  }

  /** #S-OAACS-07. */
  def findByVoData(userId: Option[UUID] = Option.empty,
                   clientId: Option[UUID] = Option.empty,
                   authCode: Option[String] = Option.empty): Future[Option[OAuthAuthorizationCodeModel]] = {
    logServiceK("findByVoData", "S-OAACS-07", userId, clientId, authCode)(
      dao.find(OAuthAuthorizationCodeVo(
        Option.empty, userId, clientId, authCode
      ))
    ).recover(optionRecover)
  }

}
