package com.crystalapps.example.backend.routers

import play.api.routing.Router.Routes
import play.api.routing.{Router, SimpleRouter}

/**
  * Created by changyenh on 西暦18/05/17.
  */
trait CommonRouter extends SimpleRouter {

  protected val base: String

  lazy val get: Router = this.withPrefix(base)

  protected def chainRoutes(allRoutes: CommonRouter*): Routes = {
    allRoutes.map(_.get.routes).reduceLeft((a,b) => a.orElse(b))
  }

}
