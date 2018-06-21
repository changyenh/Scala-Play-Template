package com.crystalapps.example.backend.database.models

import java.sql.Timestamp
import java.util.UUID

import com.crystalapps.example.backend.common.Common
import com.crystalapps.example.backend.database.models.BasicModels.{BasicFields, BasicModel, BasicTable, BasicVo}

/**
  * Created by changyenh on 西暦18/05/06.
  */
object CommonModels {

  import com.crystalapps.example.backend.database.DefaultProfile.api._

  case class CommonFields(key: Long = 0,
                          id: UUID = Common.generateUUID,
                          createdAt: Timestamp = Common.getCurrentTimestamp,
                          updatedAt: Timestamp = Common.getCurrentTimestamp,
                          active: Boolean = true) //extends BasicFields

  /*case class CommonFields(key: Long = 0,
                          id: String = Common.generateUUID) extends BasicFields*/

  /*case class CommonColumns(key: Rep[Long],
                           id: Rep[String],
                           override val createdAt: Rep[Timestamp],
                           override val updatedAt: Rep[Timestamp],
                           override val active: Rep[Boolean]) extends BasicColumns(createdAt, updatedAt, active)*/

  case class CommonColumns(key: Rep[Long],
                           id: Rep[UUID],
                           createdAt: Rep[Timestamp],
                           updatedAt: Rep[Timestamp],
                           active: Rep[Boolean])

  trait CommonModel extends BasicModel {
    def commonFields: CommonFields

    override def basicFields: BasicFields = BasicFields()

    def key: Long = commonFields.key

    def id: UUID = commonFields.id

  }

  trait CommonVo extends BasicVo {
    var key: Option[Long] = Option.empty

    var id: Option[UUID] = Option.empty

    def setKey(key: Option[Long]): this.type = {
      this.key = key
      this
    }

    def setKey(key: Long): this.type = setKey(Option(key))

    def setId(id: Option[UUID]): this.type = {
      this.id = id
      this
    }

    def setId(id: UUID): this.type = setId(Option(id))

    def build(key: Option[Long], id: Option[UUID]): this.type = setKey(key).setId(id)

    def build(key: Long, id: UUID): this.type = build(Option(key), Option(id))

  }

  abstract class CommonTable[Model <: CommonModel](tag: Tag, tableName: String) extends BasicTable[Model](tag, tableName) {
    def key = column[Long]("key", O.PrimaryKey, O.AutoInc)

    def id = column[UUID]("id", O.Unique)

  }

  implicit object CommonShape extends CaseClassShape(CommonColumns.tupled, CommonFields.tupled)

}
