package com.crystalapps.example.backend.database.models

import java.sql.Timestamp

import com.crystalapps.example.backend.common.Common

/**
  * Created by changyenh on 西暦18/05/06.
  */
object BasicModels {

  import com.crystalapps.example.backend.database.DefaultProfile.api._

  case class BasicFields(createdAt: Timestamp = Common.getCurrentTimestamp,
                         updatedAt: Timestamp = Common.getCurrentTimestamp,
                         active: Boolean = true) {
    //        implicit val basicFieldsFormat: OFormat[BasicFields] = Json.format[BasicFields]
    //        implicit val timestampFormat: OFormat[Timestamp] = Json.format[Timestamp]
  }

  case class BasicColumns(createdAt: Rep[Timestamp],
                          updatedAt: Rep[Timestamp],
                          active: Rep[Boolean]) {
    //        implicit val basicColumnsFormat: OFormat[BasicColumns] = Json.format[BasicColumns]
    //        implicit val timestampFormat: OFormat[Timestamp] = Json.format[Timestamp]
  }

  //    abstract class BasicModel(createdAt: Timestamp, updatedAt: Timestamp, active: Boolean) {
  trait BasicModel {
    def basicFields: BasicFields

    def createdAt: Timestamp = basicFields.createdAt

    def updatedAt: Timestamp = basicFields.updatedAt

    def active: Boolean = basicFields.active
  }

  trait BasicVo {
    var createdAt: Option[Timestamp] = Option.empty

    var updatedAt: Option[Timestamp] = Option.empty

    var active: Option[Boolean] = Option.empty

    def setCreatedAt(createdAt: Option[Timestamp]): this.type = {
      this.createdAt = createdAt
      this
    }

    def setCreatedAt(createdAt: Timestamp): this.type = setCreatedAt(Option(createdAt))

    def setUpdatedAt(updatedAt: Option[Timestamp]): this.type = {
      this.updatedAt = updatedAt
      this
    }

    def setUpdatedAt(updatedAt: Timestamp): this.type = setUpdatedAt(Option(updatedAt))

    def setActive(active: Option[Boolean]): this.type = {
      this.active = active
      this
    }

    def setActive(active: Boolean): this.type = setActive(Option(active))

    def build(createdAt: Option[Timestamp], updatedAt: Option[Timestamp], active: Option[Boolean]): this.type = {
      setCreatedAt(createdAt).setUpdatedAt(updatedAt).setActive(active)
    }

    def build(createdAt: Timestamp, updatedAt: Timestamp, active: Boolean): this.type = build(Option(createdAt), Option(updatedAt), Option(active))
  }

  abstract class BasicTable[Model <: BasicModel](tag: Tag, tableName: String) extends Table[Model](tag, tableName) {
    def createdAt = column[Timestamp]("createdAt")

    def updatedAt = column[Timestamp]("updatedAt")

    def active = column[Boolean]("active")
  }

  implicit object BasicShape extends CaseClassShape(BasicColumns.tupled, BasicFields.tupled)

}
