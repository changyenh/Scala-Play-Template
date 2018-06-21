package com.crystalapps.example.backend.routers.api

import com.crystalapps.example.backend.controllers.{ServiceSettingApplication, UserApplication}
import controllers.UserApplication
import com.crystalapps.example.backend.routers.CommonRouter
import controllers._
import com.crystalapps.example.backend.routers.CommonRouter
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.sird._

/**
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class ApiV1Router @Inject()(serviceSettingApplication: ServiceSettingApplication,
                            userApplication: UserApplication) extends CommonRouter {

  override protected val base: String = "/v1"

  override def routes: Routes = {
    // Service Setting
    case GET(p"/ServiceSetting") => serviceSettingApplication.all2
    case GET(p"/ServiceSetting/$name") => serviceSettingApplication.find2(name)
    case POST(p"/ServiceSetting") => serviceSettingApplication.insert2
    case PATCH(p"/ServiceSetting") => serviceSettingApplication.update2
    case DELETE(p"/ServiceSetting/$name") => serviceSettingApplication.remove2(name)
  }

}
