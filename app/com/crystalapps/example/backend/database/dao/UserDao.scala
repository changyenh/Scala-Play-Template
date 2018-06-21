package com.crystalapps.example.backend.database.dao

import com.crystalapps.example.backend.security.SecurityCommon
import javax.inject.Inject
import com.crystalapps.example.backend.database.models.CommonModels.CommonColumns
import com.crystalapps.example.backend.database.models.UserModels._
import com.crystalapps.example.backend.security.SecurityCommon
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

/**
  * Created by changyenh on 西暦18/05/04.
  */
class UserDao @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext) extends CommonDao[UserModel, UserVo](dbConfigProvider) {

  import com.crystalapps.example.backend.common.DaoHelper._
  import profile.api._

  private val tableQuery: TableQuery[ModelTable] = TableQuery[ModelTable]
  override protected val superTableQuery: TableQuery[_ <: ModelTableAbstract] = tableQuery

  /** #D-UD-01. */
  override protected def filter(vo: UserVo): profile.api.Query[_ <: ModelTableAbstract, UserModel, Seq] = {
    logDaoK("filter", "D-UD-01", vo)(
      tableQuery.optionalFilter(vo.username)(_.username === _)
          .optionalFilter(vo.password)(_.password === SecurityCommon.hashPassword(_))
          .optionalFilter(vo.name)(_.name === _)
          .optionalFilter(vo.id)(_.id === _)
    )
  }

  /** #D-UD-02. */
  override protected def filterPk(model: UserModel): profile.api.Query[_ <: ModelTableAbstract, UserModel, Seq] = {
    logDaoK("filterPk", "D-UD-02", model)(
      tableQuery.filter(_.key === model.key)
    )
  }

  class ModelTable(tag: Tag) extends ModelTableAbstract(tag, "User") {
    def username = column[String]("username")

    def password = column[String]("password")

    def name = column[String]("name")

    def * = (
        username,
        password,
        name,
        CommonColumns(key, id, createdAt, updatedAt, active)
    ) <> ((UserModel.apply _).tupled, UserModel.unapply)
  }

}
