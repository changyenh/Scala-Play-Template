package com.crystalapps.example.backend.database.dao.security

import com.crystalapps.example.backend.database.dao.`trait`.DaoTrait
import javax.inject.Inject
import com.crystalapps.example.backend.database.dao.`trait`.{BasicDaoOperations, DaoTrait}
import com.crystalapps.example.backend.database.models.SecurityModels.{OAuthClientModel, OAuthClientTable, OAuthClientVo}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/06.
  */
class OAuthClientDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
        with DaoTrait with BasicDaoOperations[OAuthClientModel, OAuthClientVo, OAuthClientTable] {

  import com.crystalapps.example.backend.common.DaoHelper._
  import profile.api._

  protected val tableQuery: TableQuery[OAuthClientTable] = TableQuery[OAuthClientTable]

  /** #D-OACD-01. */
  protected def filter(vo: OAuthClientVo): Query[OAuthClientTable, OAuthClientModel, Seq] = {
    logDaoK("filter", "D-OACD-01", vo)(
      tableQuery.optionalFilter(vo.clientId)(_.clientId === _)
          .optionalFilter(vo.clientSecret)(_.clientSecret === _)
          .optionalFilter(vo.redirectUri)(_.redirectUri === _)
          .optionalFilter(vo.grantType)(_.grantType === _)
          .optionalFilter(vo.userId)(_.userId === _)
    )
  }

  /** #D-OACD-02. */
  protected def filterPk(model: OAuthClientModel): Query[OAuthClientTable, OAuthClientModel, Seq] = {
    logDaoK("filterPk", "D-OACD-02", model)(
      tableQuery.filter(_.clientId === model.clientId)
    )
  }

  /** #D-OACD-03. */
  def all(): Future[Option[Seq[OAuthClientModel]]] = {
    logDaoK("all", "D-OACD-03")(
      db.run(tableQuery.result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OACD-04. */
  def find(vo: OAuthClientVo): Future[Option[OAuthClientModel]] = {
    logDaoK("find", "D-OACD-04", vo)(
      db.run(filter(vo).result.headOption).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OACD-05. */
  def findList(vo: OAuthClientVo): Future[Option[Seq[OAuthClientModel]]] = {
    logDaoK("findList", "D-OACD-05", vo)(
      db.run(filter(vo).result).recover(seqRecover).map(Option(_))
    ).recover(optionRecover)
  }

  /** #D-OACD-06. */
  def update(model: OAuthClientModel): Future[Option[Int]] = {
    logDaoK("update", "D-OACD-06", model)(
      db.run(filterPk(model).update(model)).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OACD-07. */
  def insertOrUpdate(model: OAuthClientModel): Future[Option[Int]] = {
    logDaoK("insertOrUpdate", "D-OACD-07", model)(
      db.run(tableQuery.insertOrUpdate(model)).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OACD-08. */
  def insert(model: OAuthClientModel): Future[Option[OAuthClientModel]] = {
    logDaoK("insert", "D-OACD-08", model)(
      db.run(
        tableQuery.returning(tableQuery) += model
      ).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

  /** #D-OACD-09. */
  def remove(vo: OAuthClientVo): Future[Option[Int]] = {
    logDaoK("remove", "D-OACD-09", vo)(
      db.run(filter(vo).delete).map(Option(_)).recover(optionRecover)
    ).recover(optionRecover)
  }

}