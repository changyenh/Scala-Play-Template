package com.crystalapps.example.backend.routers

import com.crystalapps.example.backend.controllers.SecurityApplication
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.sird._

/** w
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class OAuthRouter @Inject()(securityApplication: SecurityApplication) extends CommonRouter {

  override protected val base: String = "/oauth"

  override def routes: Routes = {
    // Security Application (OAuth2)
    case GET(p"/token") => securityApplication.accessToken
    case POST(p"/token") => securityApplication.accessToken
  }
}
