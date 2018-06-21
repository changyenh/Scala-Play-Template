package com.crystalapps.example.backend.controllers

import com.crystalapps.example.backend.common.CommonResponse
import com.crystalapps.example.backend.controllers.`trait`.ControllerTrait
import com.crystalapps.example.backend.settings.ServiceSetting
import javax.inject.Inject
import play.api.cache._
import play.api.mvc.{Action, AnyContent, EssentialAction}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/** StartUp Codes and Index.
  * Inject the Initial Object.
  * */
class Application @Inject()(cached: Cached,
                            asyncCache: AsyncCacheApi,
                            serviceSetting: ServiceSetting)
                           (implicit executionContext: ExecutionContext) extends ControllerTrait {

  import com.crystalapps.example.backend.common.JsonConversions._

  def index: EssentialAction = cached("ready").default(1.hour).build {
    logApiKOS("index", "A-A-01")((opName, sn) =>
      Action(parse.empty) {
        warn(ServiceSetting.FCM_URL)
        handleResponse(CommonResponse.ok("Ready"), opName, sn)
      }
    )
  }

  def clearCache: Action[AnyContent] = logApiKOS("clearCache", "A-A-02")((opName, sn) =>
      Action {
        handleResponse({
          if (asyncCache.removeAll().isCompleted) {
            CommonResponse.ok[Boolean]
          } else {
            CommonResponse.unknownError[Boolean]
          }
        }, opName, sn)
      }
  )

  /*Action {
    try {
      Await.result(asyncCache.removeAll(), 30.seconds)

    } catch {
      case _: Throwable => CommonResponse.unexpectedError[Int]
    }
  }*/

}

