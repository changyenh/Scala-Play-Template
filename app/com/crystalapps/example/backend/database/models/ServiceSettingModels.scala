package com.crystalapps.example.backend.database.models

import com.crystalapps.example.backend.database.models.BasicModels.{BasicFields, BasicModel, BasicVo}

/**
  * Created by changyenh on 西暦18/05/08.
  */
object ServiceSettingModels {

  case class ServiceSettingModel(serviceName: String, serviceValue: String, basicFields: BasicFields = BasicFields()) extends BasicModel

  object ServiceSettingModel {
    //        implicit val serviceSettingReads: Reads[ServiceSettingModel] = basicReads(Json.reads[ServiceSettingModel])
    //        implicit val serviceSettingWrites: Writes[ServiceSettingModel] = basicWrites(Json.writes[ServiceSettingModel])
  }

  case class ServiceSettingVo(serviceName: Option[String] = Option.empty, serviceValue: Option[String] = Option.empty) extends BasicVo

  object ServiceSettingVo {
    def apply(serviceName: String, serviceValue: String): ServiceSettingVo = new ServiceSettingVo(Option(serviceName), Option(serviceValue))

    def apply(serviceName: String): ServiceSettingVo = new ServiceSettingVo(Option(serviceName), Option.empty)
  }

}
