package com.crystalapps.example.backend.common

import javax.inject.Singleton

import com.crystalapps.example.backend.common.CommonResponse.StatusCode
import play.api.http.HttpErrorHandler
import play.api.mvc._

import scala.concurrent._

/**
  * Created by changyenh on 西暦18/05/04.
  */

@Singleton
class ErrorHandler extends HttpErrorHandler with CommonResponseHandler {

  import JsonConversions._

  /** #F-EH-01. */
  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logFuncK("onServerError", "F-EH-01", request)({
      logParamsError("ErrorHandler-onServerError", request)
      error(exception)
      Future.successful(handleResponse(CommonResponse.unexpectedError[Boolean], "onServerError", "F-EH-01"))
    })
    /*logParamsError("onServerError", request)
    error(exception)
    Future.successful(handleResponse(CommonResponse.unexpectedError[Boolean])())*/
  }

  /** #F-EH-02. */
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logFuncK("onClientError", "F-EH-02", request, statusCode, message)({
      logParamsError("ErrorHandler-onClientError", request, statusCode, message)
      Future.successful(createResponse(handleResponseByHttpStatus(statusCode, StatusCode.BadRequest), statusCode))
    })
    /*logParamsError("onClientError", request, statusCode)
    error(message)
    Future.successful(createResponse(handleResponseByHttpStatus(statusCode, StatusCode.BadRequest), statusCode))*/
    /*statusCode match {
        case code if code >= 400 && code < 500 => {
            error(message)
            Future.successful(Forbidden(views.html.errors.notFoundPage(statusCode)))
        }
        case _ => {
            Future.successful(NotFound(views.html.errors.notFoundPage(statusCode)))
        }
    }*/
  }

}