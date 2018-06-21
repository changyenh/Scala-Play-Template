package com.crystalapps.example.backend.services

import com.crystalapps.example.backend.database.models.ServiceSettingModels.{ServiceSettingModel, ServiceSettingVo}
import com.crystalapps.example.backend.services.dto.ServiceSettingServiceDto.ServiceSettingDto
import javax.inject.Inject
import com.crystalapps.example.backend.database.dao.ServiceSettingDao
import com.crystalapps.example.backend.database.models.ServiceSettingModels._
import com.crystalapps.example.backend.services.dto.ServiceSettingServiceDto.ServiceSettingDto

import scala.concurrent.ExecutionContext

/**
  * Created by changyenh on 西暦18/05/09.
  */
class ServiceSettingService @Inject()(dao: ServiceSettingDao)(implicit executionContext: ExecutionContext)
    extends
        BasicService[ServiceSettingModel,
            ServiceSettingVo,
            ServiceSettingDao,
            ServiceSettingDto](dao)(executionContext, ServiceSettingDto.transformerSSD2SSM) {


}
