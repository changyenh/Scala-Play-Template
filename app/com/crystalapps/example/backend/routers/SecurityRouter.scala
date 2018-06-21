package com.crystalapps.example.backend.routers

import com.crystalapps.example.backend.controllers.SecurityApplication
import controllers.Assets
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.sird._

/**
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class SecurityRouter @Inject()(securityApplication: SecurityApplication,
                               assets: Assets) extends CommonRouter {

  override protected val base: String = "/security"

  override def routes: Routes = {
    // Security Application
    case GET(p"/hash/pass/$pass") => securityApplication.hashPassword(pass)
//    case GET(p"/login" ? q_o"redirect=$redirectUri") => securityApplication.loginPage(redirectUri)
//    case POST(p"/login") => securityApplication.login
  }

}
