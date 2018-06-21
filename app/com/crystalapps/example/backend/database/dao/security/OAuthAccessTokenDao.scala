package com.crystalapps.example.backend.database.dao.security

import javax.inject.Inject

import com.crystalapps.example.backend.database.dao.`trait`.{BasicDaoOperations, DaoTrait}
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthAccessTokenModel, OAuthAccessTokenTable, OAuthAccessTokenVo}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/06.
  */
class OAuthAccessTokenDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
        with DaoTrait with BasicDaoOperations[OAuthAccessTokenModel, OAuthAccessTokenVo, OAuthAccessTokenTable] {

  import com.crystalapps.example.backend.common.DaoHelper._
  import profile.api._

  protected val tableQuery: TableQuery[OAuthAccessTokenTable] = TableQuery[OAuthAccessTokenTable]

  /** #D-OAATD-01. */
  protected def filter(vo: OAuthAccessTokenVo): Query[OAuthAccessTokenTable, OAuthAccessTokenModel, Seq] = {
    logDaoK("filter", "D-OAATD-01", vo)(
      tableQuery.optionalFilter(vo.key)(_.key === _)
          .optionalFilter(vo.userId)(_.userId === _)
          .optionalFilter(vo.clientId)(_.clientId === _)
          .optionalFilter(vo.accessToken)(_.accessToken === _)
          .optionalFilter(vo.refreshToken)(_.refreshToken === _)
    )
  }

  /** #D-OAATD-02. */
  protected def filterPk(model: OAuthAccessTokenModel): Query[OAuthAccessTokenTable, OAuthAccessTokenModel, Seq] = {
    logDaoK("filterPk", "D-OAATD-02", model)(
      tableQuery.filter(_.key === model.key)
    )
  }

  /** #D-OAATD-03. */
  def all(): Future[Option[Seq[OAuthAccessTokenModel]]] = {
    logDaoK("all", "D-OAATD-03")(
      db.run(tableQuery.result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OAATD-04. */
  def find(vo: OAuthAccessTokenVo): Future[Option[OAuthAccessTokenModel]] = {
    logDaoK("find", "D-OAATD-04", vo)(
      db.run(filter(vo).result.headOption).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAATD-05. */
  def findList(vo: OAuthAccessTokenVo): Future[Option[Seq[OAuthAccessTokenModel]]] = {
    logDaoK("findList", "D-OAATD-05", vo)(
      db.run(filter(vo).result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OAATD-06. */
  def update(model: OAuthAccessTokenModel): Future[Option[Int]] = {
    logDaoK("update", "D-OAATD-06", model)(
      db.run(filterPk(model).update(model)).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAATD-07. */
  def insertOrUpdate(model: OAuthAccessTokenModel): Future[Option[Int]] = {
    logDaoK("insertOrUpdate", "D-OAATD-07", model)(
      db.run(tableQuery.insertOrUpdate(model)).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAATD-08. */
  def insert(model: OAuthAccessTokenModel): Future[Option[OAuthAccessTokenModel]] = {
    logDaoK("insert", "D-OAATD-08", model)(
      db.run(
        tableQuery.returning(tableQuery.map(_.key))
            .into((token, key) => token.copy(key = key)) += model
      ).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OAATD-09. */
  def remove(vo: OAuthAccessTokenVo): Future[Option[Int]] = {
    logDaoK("remove", "D-OAATD-09", vo)(
      db.run(filter(vo).delete).map(Option(_)).recover(optionRecover[Int])
    ).recover(optionRecover)
  }

}