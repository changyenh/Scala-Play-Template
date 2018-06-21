package com.crystalapps.example.backend.database.dao.security

import javax.inject.Inject

import com.crystalapps.example.backend.database.dao.`trait`.{BasicDaoOperations, DaoTrait}
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthAuthorizationCodeModel, OAuthAuthorizationCodeTable, OAuthAuthorizationCodeVo}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/06.
  */
class OAuthAuthorizationCodeDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                         (implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
        with DaoTrait with BasicDaoOperations[OAuthAuthorizationCodeModel, OAuthAuthorizationCodeVo, OAuthAuthorizationCodeTable] {

  import com.crystalapps.example.backend.common.DaoHelper._
  import profile.api._

  protected val tableQuery: TableQuery[OAuthAuthorizationCodeTable] = TableQuery[OAuthAuthorizationCodeTable]

  /** #D-OAACD-01. */
  protected def filter(vo: OAuthAuthorizationCodeVo): Query[OAuthAuthorizationCodeTable, OAuthAuthorizationCodeModel, Seq] = {
    logDaoK("filter", "D-OAACD-01", vo)(
      tableQuery.optionalFilter(vo.key)(_.key === _)
          .optionalFilter(vo.userId)(_.userId === _)
          .optionalFilter(vo.clientId)(_.clientId === _)
          .optionalFilter(vo.authCode)(_.authCode === _)
    )
  }

  /** #D-OAACD-02. */
  protected def filterPk(model: OAuthAuthorizationCodeModel): Query[OAuthAuthorizationCodeTable, OAuthAuthorizationCodeModel, Seq] = {
    logDaoK("filterPk", "D-OAACD-02", model)(
      tableQuery.filter(_.key === model.key)
    )
  }

  /** #D-OAACD-03. */
  def all(): Future[Option[Seq[OAuthAuthorizationCodeModel]]] = {
    logDaoK("all", "D-OAACD-03")(
      db.run(tableQuery.result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OAACD-04. */
  def find(vo: OAuthAuthorizationCodeVo): Future[Option[OAuthAuthorizationCodeModel]] = {
    logDaoK("find", "D-OAACD-04", vo)(
      db.run(filter(vo).result.headOption).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAACD-05. */
  def findList(vo: OAuthAuthorizationCodeVo): Future[Option[Seq[OAuthAuthorizationCodeModel]]] = {
    logDaoK("findList", "D-OAACD-05", vo)(
      db.run(filter(vo).result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OAACD-06. */
  def update(model: OAuthAuthorizationCodeModel): Future[Option[Int]] = {
    logDaoK("update", "D-OAACD-06", model)(
      db.run(filterPk(model).update(model)).map(Option(_)).recover(optionRecover)
    )
  }.recover(optionRecover)

  /** #D-OAACD-07. */
  def insertOrUpdate(model: OAuthAuthorizationCodeModel): Future[Option[Int]] = {
    logDaoK("insertOrUpdate", "D-OAACD-07", model)(
      db.run(tableQuery.insertOrUpdate(model)).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAACD-08. */
  def insert(model: OAuthAuthorizationCodeModel): Future[Option[OAuthAuthorizationCodeModel]] = {
    logDaoK("insert", "D-OAACD-08", model)(
      db.run(tableQuery.returning(tableQuery.map(_.key))
          .into((code, key) => code.copy(key = key)) += model
      ).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAACD-09. */
  def remove(vo: OAuthAuthorizationCodeVo): Future[Option[Int]] = {
    logDaoK("remove", "D-OAACD-09", vo)(
      db.run(filter(vo).delete).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

}