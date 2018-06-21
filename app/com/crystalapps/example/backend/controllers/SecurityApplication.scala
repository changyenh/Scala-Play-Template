package com.crystalapps.example.backend.controllers

import com.crystalapps.example.backend._
import com.crystalapps.example.backend.common.{Common, CommonResponse}
import com.crystalapps.example.backend.controllers.`trait`.ControllerTrait
import com.crystalapps.example.backend.security.{SecurityCommon, SecurityDataHandler}
import com.crystalapps.example.backend.services.UserService
import com.crystalapps.example.backend.services.dto.LoginServiceDto.LoginDto
import com.crystalapps.example.backend.services.security.OAuthClientService
import javax.inject.Inject
import play.api.mvc.{Action, AnyContent}
import scalaoauth2.provider.OAuthGrantType
import com.crystalapps.example.backend.views._

import scala.concurrent.ExecutionContext

/**
  * Created by changyenh on 西暦18/05/15.
  * Should separate to individual api.
  */
class SecurityApplication @Inject()(securityDataHandler: SecurityDataHandler,
                                    userService: UserService,
                                    clientService: OAuthClientService)
                                   (implicit executionContext: ExecutionContext) extends ControllerTrait {

  /** #A-SA-01. */
  def accessToken: Action[AnyContent] = Action.async { implicit request =>
    logApiK("accessToken", "A-SA-01")(
      issueAccessToken(
        securityDataHandler
      )
    )
  }

  def login: Action[LoginDto] = logApiKOS("login", "A-SA-02")((opName, sn) =>
    Action(validateJson[LoginDto]).async { request =>
      tryRecover(
        userService.loginDirect(request.body).map(res =>
          handleResponse(res, opName, sn)(writeable(res))
        )
      )
    }
  )

  def loginPage(redirectUri: Option[String]): Action[AnyContent] = logApiK("loginPage", "A-SA-03")(
    Action {
      Ok(html.login.render(redirectUri))
    }
  )

  def loginRedirect: Action[LoginDto] = logApiK("loginRedirect", "A-SA-04")(
    Action(validateJson[LoginDto]).async { request =>
      val clientFuture = for {
        userOpt <- userService.findByVoData(
          Some(request.body.username),
          Some(request.body.password)
        ).recover(optionRecover)
        clientFuture <-
          clientService.findByVoData(
            userId = userOpt.map(_.id),
            grantType = Some(OAuthGrantType.PASSWORD)
          ).recover(optionRecover)
      } yield clientFuture
      val a: routers.SecurityRouter
      clientFuture.map(clientOpt => {
        Redirect(
          "../oauth/token",
          Common.mkQueryStringMapWithoutEmpty(
            "grant_type" -> OAuthGrantType.PASSWORD,
            "username" -> request.body.username,
            "password" -> request.body.password,
            "client_id" -> clientOpt.map(client => Common.UUIDToString(client.clientId)),
            "client_secret" -> clientOpt.map(_.clientSecret)
          )
        )
      })
    }
  )

  /** #A-SA-04. */
  def hashPassword(password: String): Action[AnyContent] = Action {
    logApiKOS("hashPassword", "A-SA-05")((opName, sn) =>
      handleResponse(CommonResponse.ok(
        SecurityCommon.hashPassword(password)
      ), opName, sn)
    )
  }

  /*def test = AuthorizedAction(securityDataHandler)(validateJson[ServiceSettingModel]).async { request =>
    tryRecover(
      Future.successful(handleResponse(CommonResponse.ok, "", "")(writeable(CommonResponse.ok)))
    )
  }*/
  /*def authorization*/

}
