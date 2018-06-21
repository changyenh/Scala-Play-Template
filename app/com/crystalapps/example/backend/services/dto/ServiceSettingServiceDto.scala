package com.crystalapps.example.backend.services.dto

import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel
import com.crystalapps.example.backend.services.dto.BasicServiceDto.{BasicDtoFields, BasicDtoModel}
import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel
import com.crystalapps.example.backend.services.dto.BasicServiceDto.{BasicDtoFields, BasicDtoModel}

/**
  * Created by changyenh on 西暦18/05/12.
  */
object ServiceSettingServiceDto {

  case class ServiceSettingDto(serviceName: Option[String], serviceValue: Option[String], basicDtoFields: BasicDtoFields)
      extends BasicDtoModel

  object ServiceSettingDto {
    val transformerSSD2SSM: DtoTransformer[ServiceSettingDto, ServiceSettingModel] = new DtoTransformer[ServiceSettingDto, ServiceSettingModel] {
      override def from(model: ServiceSettingModel): ServiceSettingDto = {
        ServiceSettingDto(
          Option(model.serviceName),
          Option(model.serviceValue),
          BasicDtoFields.from(model.basicFields)
        )
      }

      override def to(dto: ServiceSettingDto): ServiceSettingModel = {
        ServiceSettingModel(
          dto.serviceName.orNull,
          dto.serviceValue.orNull,
          BasicDtoFields.to(dto.basicDtoFields)
        )
      }
    }

  }

}
