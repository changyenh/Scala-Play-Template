package com.crystalapps.example.backend.services.dto

import java.sql.Timestamp
import java.util.UUID

import com.crystalapps.example.backend.common.Common
import com.crystalapps.example.backend.database.models.BasicModels.BasicFields
import com.crystalapps.example.backend.database.models.CommonModels.CommonFields

/**
  * Created by changyenh on 西暦18/05/12.
  */
object BasicServiceDto {

  case class BasicDtoFields(createdAt: Option[Timestamp],
                            updatedAt: Option[Timestamp])

  object BasicDtoFields extends DtoTransformer[BasicDtoFields, BasicFields] {
    override def from(model: BasicFields): BasicDtoFields = {
      BasicDtoFields(Option(model.createdAt), Option(model.updatedAt))
    }

    override def to(dto: BasicDtoFields): BasicFields = {
      BasicFields(
        dto.createdAt.getOrElse(Common.getCurrentTimestamp),
        dto.createdAt.getOrElse(Common.getCurrentTimestamp),
        true
      )
    }
  }

  case class CommonDtoFields(id: Option[UUID],
                             createdAt: Option[Timestamp],
                             updatedAt: Option[Timestamp])

  object CommonDtoFields extends DtoTransformer[CommonDtoFields, CommonFields] {
    override def from(model: CommonFields): CommonDtoFields = {
      CommonDtoFields(
        Option(model.id),
        Option(model.createdAt),
        Option(model.updatedAt)
      )
    }

    override def to(dto: CommonDtoFields): CommonFields = {
      CommonFields(
        0,
        dto.id.orNull,
        dto.createdAt.getOrElse(Common.getCurrentTimestamp),
        dto.createdAt.getOrElse(Common.getCurrentTimestamp)
      )
    }
  }

  trait BasicDtoModel {
    def basicDtoFields: BasicDtoFields

    def createdAt: Option[Timestamp] = basicDtoFields.createdAt

    def updatedAt: Option[Timestamp] = basicDtoFields.updatedAt

  }

  trait CommonDtoModel extends BasicDtoModel {
    def commonDtoFields: CommonDtoFields

    override def basicDtoFields: BasicDtoFields = BasicDtoFields(commonDtoFields.createdAt, commonDtoFields.updatedAt)

    def id: Option[UUID] = commonDtoFields.id

  }

}
