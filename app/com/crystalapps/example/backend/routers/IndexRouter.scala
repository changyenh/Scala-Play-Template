package com.crystalapps.example.backend.routers

import com.crystalapps.example.backend.controllers.Application
import controllers._
import controllers.{Assets, AssetsComponents}
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.sird._

/**
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class IndexRouter @Inject()(application: Application,
                            assets: Assets) extends CommonRouter {

  override protected val base: String = ""

  override def routes: Routes = {
    // Application
    case GET(p"/") => application.index
    case POST(p"/cache") => application.clearCache
    case GET(p"/assets/$file*") => assets.versioned(file)
    // case GET(p"/assets/$file*") => assets.at(path="/public", file)
  }
}
