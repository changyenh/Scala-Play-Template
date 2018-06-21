package com.crystalapps.example.backend.routers

import javax.inject.Inject
import play.api.routing.Router.Routes

/**
  * Created by changyenh on 西暦18/05/16.
  */
//@Singleton
class MainRouter @Inject()(index: IndexRouter,
                           oauth: OAuthRouter,
                           security: SecurityRouter,
                           api: ApiRouter) extends CommonRouter {

  override protected val base: String = ""

  override def routes: Routes = chainRoutes(index, oauth, security, api)
}
