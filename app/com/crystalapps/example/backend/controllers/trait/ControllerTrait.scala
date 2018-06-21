package com.crystalapps.example.backend.controllers.`trait`

import com.crystalapps.example.backend.common.{CommonResponse, FutureRecover}
import com.crystalapps.example.backend.common.{CommonResponse, FutureRecover}
import play.api.libs.json.{JsError, Reads}
import play.api.mvc.{BodyParser, InjectedController, Result}
import com.crystalapps.example.backend.security.MyTokenEndpoint

import scala.concurrent.{ExecutionContext, Future}
import scalaoauth2.provider.{OAuth2Provider, OAuth2ProviderActionBuilders}

/**
  * Created by changyenh on 西暦18/05/08.
  */
trait ControllerTrait extends InjectedController with FutureRecover with OAuth2Provider with OAuth2ProviderActionBuilders {

  override val tokenEndpoint = new MyTokenEndpoint()

  /** #T-CT-01. */
  protected def validateJson[A: Reads](implicit executionContext: ExecutionContext): BodyParser[A] = {
    logApiK("validateJson", "T-CT-01")(
      parse.json
          .validate(
            _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
          )
    )
  }

  /** #T-CT-02. */
  protected def tryCatchRecover[T](action: => Future[CommonResponse[T]],
                                   map: (CommonResponse[T]) => Result,
                                   recover: => PartialFunction[Throwable, Result] = resultRecover)
                                  (implicit executionContext: ExecutionContext): Future[Result] = {
    logApiK("tryCatchRecover", "T-CT-02")(
      action.map(res => map.apply(res)).recover(recover)
    )
  }

  /** #T-CT-03. */
  protected def tryRecover(action: => Future[Result],
                           recover: => PartialFunction[Throwable, Result] = resultRecover)
                          (implicit executionContext: ExecutionContext): Future[Result] = {
    logApiK("tryRecover", "T-CT-03")(
      action.recover(recover)
    )
  }

}
