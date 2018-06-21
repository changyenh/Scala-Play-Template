package com.crystalapps.example.backend.database.dao

import com.crystalapps.example.backend.database.models.BasicModels.BasicTable
import com.crystalapps.example.backend.database.models.CommonModels.CommonModel
import com.crystalapps.example.backend.database.models.BasicModels.BasicTable
import com.crystalapps.example.backend.database.models.CommonModels.{CommonModel, CommonTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/06.
  */
abstract class CommonDao[M <: CommonModel, V](override protected val dbConfigProvider: DatabaseConfigProvider)
                                             (implicit executionContext: ExecutionContext)
//        extends HasDatabaseConfigProvider[JdbcProfile] {
    extends BasicDao[M, V](dbConfigProvider) with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  protected val superTableQuery: TableQuery[_ <: BasicTable[M]]

  /** #D-CD-01. */
  override protected def filter(vo: V): Query[_ <: BasicTable[M], M, Seq]

  /** #D-CD-02. */
  override protected def filterPk(model: M): Query[_ <: BasicTable[M], M, Seq]

  /** #D-CD-03. */
  override def all(): Future[Option[Seq[M]]] = {
    logDaoK("all", "D-CD-03")(
      db.run(superTableQuery.result).map(Option(_)).recover(optionRecover[Seq[M]])
    )
  }

  /** #D-CD-04. */
  override def find(vo: V): Future[Option[M]] = {
    logDaoK("find", "D-CD-04", vo)(
      db.run(filter(vo).result.headOption).recover(optionRecover[M])
    )
  }

  /** #D-CD-05. */
  override def findList(vo: V): Future[Option[Seq[M]]] = {
    logDaoK("findList", "D-CD-05", vo)(
      db.run(filter(vo).result).map(Option(_)).recover(optionRecover[Seq[M]])
    )
  }

  /** #D-CD-06. */
  override def update(model: M): Future[Option[Int]] = {
    logDaoK("update", "D-CD-06", model)(
      db.run(filterPk(model).update(model)).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-CD-07. */
  override def insertOrUpdate(model: M): Future[Option[Int]] = {
    logDaoK("insertOrUpdate", "D-CD-07", model)(
      db.run(superTableQuery.insertOrUpdate(model)).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-CD-08. */
  override def insert(model: M): Future[Option[Int]] = {
    logDaoK("insert", "D-CD-08", model)(
      db.run(superTableQuery += model).map(Option(_)).recover(optionRecover[Int])
    )
  }

  /** #D-CD-09. */
  override def remove(vo: V): Future[Option[Int]] = {
    logDaoK("remove", "D-CD-09", vo)(
      db.run(filter(vo).delete).map(Option(_)).recover(optionRecover[Int])
    )
  }

  abstract class ModelTableAbstract(tag: Tag, tableName: String) extends CommonTable[M](tag, tableName)

}
