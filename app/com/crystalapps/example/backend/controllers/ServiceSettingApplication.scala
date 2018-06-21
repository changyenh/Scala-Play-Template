package com.crystalapps.example.backend.controllers

import com.crystalapps.example.backend.controllers.`trait`.ServiceSettingTrait
import com.crystalapps.example.backend.database.models.ServiceSettingModels.{ServiceSettingModel, _}
import com.crystalapps.example.backend.services.ServiceSettingService
import com.crystalapps.example.backend.services.dto.ServiceSettingServiceDto.ServiceSettingDto
import com.crystalapps.example.backend.settings.ServiceSetting
import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.ExecutionContext

class ServiceSettingApplication @Inject()(service: ServiceSettingService)
                                         (implicit executionContext: ExecutionContext) extends ServiceSettingTrait {

  import com.crystalapps.example.backend.common.JsonConversions._

  /**
    * The default body parser that’s used if you do not explicitly select a body parser will look at the incoming Content-Type header, and parses the body accordingly. So for example, a Content-Type of type application/json will be parsed as a JsValue, while a Content-Type of application/t-www-form-urlencoded will be parsed as a Map[String, Seq[String]].
    */

  /** #A-SSA-01 all. */
  override def all: Action[AnyContent] = logApiKOS("all", "A-SSA-01")((opName, sn) =>
    Action(parse.empty).async {
      tryRecover(
        service.all.map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-02 insert. */
  override def insert: Action[ServiceSettingModel] = logApiKOS("insert", "A-SSA-02")((opName, sn) =>
    Action(validateJson[ServiceSettingModel]).async { request =>
      tryRecover(
        service.insert(request.body).map(res =>
          handleResponse(res, opName, sn)(writeable(res)
          )
        )
      )
    }
  )

  /** #A-SSA-03 update. */
  override def update: Action[ServiceSettingModel] = logApiKOS("update", "A-SSA-03")((opName, sn) =>
    Action(validateJson[ServiceSettingModel]).async { request =>
      tryRecover(
        service.update(request.body).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-04 find. */
  override def find(name: String): Action[AnyContent] = logApiKOS("find", "A-SSA-04")((opName, sn) =>
    Action(parse.empty).async {
      tryRecover(
        service.find(ServiceSettingVo(name)).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-05 remove. */
  override def remove(name: String): Action[AnyContent] = logApiKOS("remove", "A-SSA-05")((opName, sn) =>
    Action(parse.empty).async {
      tryCatchRecover[Int](
        service.remove(ServiceSettingVo(name)),
        res => handleResponse(res, opName, sn)(writeable(res))
      )
    }
  )




  /** #A-SSA-01 all. */
  def all2: Action[AnyContent] = logApiKOS("all", "A-SSA-01")((opName, sn) =>
    Action(parse.empty).async {
      warn(ServiceSetting.FCM_URL)
      tryRecover(
        service.all2.map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-02 insert. */
  def insert2: Action[ServiceSettingDto] = logApiKOS("insert2", "A-SSA-02-2")((opName, sn) =>
    Action(validateJson[ServiceSettingDto]).async { request =>
      tryRecover(
        service.insert2(request.body).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-03 update. */
  def update2: Action[ServiceSettingDto] = logApiKOS("update", "A-SSA-03")((opName, sn) =>
    Action(validateJson[ServiceSettingDto]).async { request =>
      tryRecover(
        service.update2(request.body).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-04 find. */
  def find2(name: String): Action[AnyContent] = logApiKOS("find", "A-SSA-04")((opName, sn) =>
    Action(parse.empty).async {
      tryRecover(
        service.find2(ServiceSettingVo(name)).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  /** #A-SSA-05 remove. */
  def remove2(name: String): Action[AnyContent] = logApiKOS("remove", "A-SSA-05")((opName, sn) =>
    Action(parse.empty).async {
      tryCatchRecover[Int](
        service.remove2(ServiceSettingVo(name)),
        res => handleResponse(res, opName, sn)(writeable(res))
      )
    }
  )

}



