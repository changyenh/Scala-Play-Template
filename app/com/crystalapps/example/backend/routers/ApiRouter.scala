package com.crystalapps.example.backend.routers

import com.crystalapps.example.backend.routers.api.ApiV1Router
import javax.inject.Inject
import play.api.routing.Router.Routes

/**
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class ApiRouter @Inject()(v1: ApiV1Router) extends CommonRouter {

  override protected val base: String = "/api"

  override def routes: Routes = chainRoutes(v1)

}
