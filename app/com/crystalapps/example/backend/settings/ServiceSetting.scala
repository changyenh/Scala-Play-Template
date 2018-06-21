package com.crystalapps.example.backend.settings

import com.crystalapps.example.backend.common.CommonClass
import javax.inject.{Inject, Singleton}
import com.crystalapps.example.backend.common.CommonClass
import com.crystalapps.example.backend.database.dao.ServiceSettingDao

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by changyenh on 西暦18/05/04.
  */
@Singleton
class ServiceSetting @Inject()(serviceSettingDao: ServiceSettingDao) {
  ServiceSetting.loadServiceSettings(serviceSettingDao)
}

object ServiceSetting extends CommonClass {

  private val serviceNamePrefix = "CM-"
  private val serviceSettings = mutable.Map.empty[String, Any]

  def FCM_URL: String = getServiceSetting("FCM_URL")

  def FCM_SERVER_KEY: String = getServiceSetting("FCM_SERVER_KEY")

  /** #I-F-SS-01. */
  private def loadServiceSettings(serviceSettingDao: ServiceSettingDao): Unit = {
    logFuncK("loadServiceSettings", "I-F-SS-01")(
      serviceSettingDao.all().onComplete {
        case Success(result) =>
          serviceSettings.empty
          result.get.foreach(serviceSetting => {
            serviceSettings.put(serviceSetting.serviceName, serviceSetting.serviceValue)
          })
        case Failure(ex) =>
          error(ex)
      }
    )
    /*serviceSettingDao.all().onComplete {
      case Success(result) =>
        serviceSettings.empty
        result.get.foreach(serviceSetting => {
          serviceSettings.put(serviceSetting.serviceName, serviceSetting.serviceValue)
        })
      case Failure(ex) =>
        error(ex)
    }*/
  }

  /** #I-F-SS-02. */
  private def getServiceSetting[A](name: String, default: A = ""): A = {
    logFuncK("getServiceSetting", "I-F-SS-02")(
      try {
        serviceSettings.getOrElse(serviceNamePrefix + name, default).asInstanceOf[A]
      } catch {
        case ex: Exception => {
          error(ex)
          default
        }
      }
    )
  }

}
