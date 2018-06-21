package com.crystalapps.example.backend.database.dao

import com.crystalapps.example.backend.database.dao.`trait`.DaoTrait
import com.crystalapps.example.backend.database.models.BasicModels.{BasicModel, BasicTable}
import com.crystalapps.example.backend.database.dao.`trait`.{BasicDaoOperations, DaoTrait}
import com.crystalapps.example.backend.database.models.BasicModels.{BasicModel, BasicTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/06.
  */
abstract class BasicDao[M <: BasicModel, V](protected val dbConfigProvider: DatabaseConfigProvider)
                                           (implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] with DaoTrait {//with BasicDaoOperations[M, V, _ <: BasicTable[M]] {

  import profile.api._

  protected val superTableQuery: TableQuery[_ <: BasicTable[M]]

  /** #D-BD-01. */
  protected def filter(vo: V): Query[_ <: BasicTable[M], M, Seq]

  /** #D-BD-02. */
  protected def filterPk(model: M): Query[_ <: BasicTable[M], M, Seq]

  /** #D-BD-03. */
  def all(): Future[Option[Seq[M]]] = {
    logDaoK("all", "D-BD-03")(
      db.run(superTableQuery.result).map(Option(_)).recover(optionRecover[Seq[M]])
    )
  }

  /** #D-BD-04. */
  def find(vo: V): Future[Option[M]] = {
    logDaoK("find", "D-BD-04", vo)(
      db.run(filter(vo).result.headOption).recover(optionRecover[M])
    )
  }

  /** #D-BD-05. */
  def findList(vo: V): Future[Option[Seq[M]]] = {
    logDaoK("findList", "D-BD-05", vo)(
      db.run(filter(vo).result).map(Option(_)).recover(optionRecover[Seq[M]])
    )
  }

  /** #D-BD-06. */
  def update(model: M): Future[Option[Int]] = {
    logDaoK("update", "D-BD-06", model)(
      db.run(filterPk(model).update(model)).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-BD-07. */
  def insertOrUpdate(model: M): Future[Option[Int]] = {
    logDaoK("insertOrUpdate", "D-BD-07", model)(
      db.run(superTableQuery.insertOrUpdate(model)).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-BD-08. */
  def insert(model: M): Future[Option[Int]] = {
    logDaoK("insert", "D-BD-08", model)(
      db.run(superTableQuery += model).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-BD-09. */
  def remove(vo: V): Future[Option[Int]] = {
    logDaoK("remove", "D-BD-09", vo)(
      db.run(filter(vo).delete).map(Option(_)).recover(optionRecover[Int])
    )
  }

  abstract class ModelTableAbstract(tag: Tag, tableName: String) extends BasicTable[M](tag, tableName)

}
