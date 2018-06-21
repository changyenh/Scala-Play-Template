package com.crystalapps.example.backend.common

import com.crystalapps.example.backend.common.CommonResponse.StatusCode
import com.crystalapps.example.backend.common.CommonResponse.StatusCode.StatusCode
import play.api.http.Status._
import play.api.http.Writeable
import play.api.mvc.{ResponseHeader, Result}

/**
  * Created by changyenh on 西暦18/05/09.
  */
trait CommonResponseHandler extends CommonClass {

  /** #T-CRH-01. */
  protected def writeable[T](content: T)(implicit writeable: Writeable[T]): Writeable[T] = {
    logFuncK("writeable", "T-CRH-01", content)(
      writeable
    )
  }

  /** #T-CRH-02. */
  protected def getResponseHeader(statusCode: StatusCode, successStatus: Int = OK): ResponseHeader = {
    logFuncK("getResponseHeader", "T-CRH-02", statusCode, successStatus)({
      val status = statusCode match {
        case StatusCode.NotFound => NOT_FOUND
        case StatusCode.BadRequest => BAD_REQUEST
        case StatusCode.Forbidden => FORBIDDEN
        case StatusCode.InsertionError => UNPROCESSABLE_ENTITY
        case StatusCode.UpdateError => UNPROCESSABLE_ENTITY
        case StatusCode.DeletionError => UNPROCESSABLE_ENTITY
        case StatusCode.UnexpectedError => INTERNAL_SERVER_ERROR
        case StatusCode.UnknownError => INTERNAL_SERVER_ERROR
        case _: StatusCode => successStatus
      }
      ResponseHeader(status)
    })
  }

  /** #T-CRH-03. */
  protected def getCommonResponseByHttpStatus(statusCode: Int, successStatus: StatusCode = StatusCode.Ok): CommonResponse[Boolean] = {
    logFuncK("getCommonResponseByHttpStatus", "T-CRH-03", statusCode, successStatus)({
      val status = statusCode match {
        case NOT_FOUND => StatusCode.NotFound
        case BAD_REQUEST => StatusCode.BadRequest
        case FORBIDDEN => StatusCode.Forbidden
        case INTERNAL_SERVER_ERROR => StatusCode.UnexpectedError
        case code if 400 until 500 contains code => StatusCode.Forbidden
        case code if 500 until 600 contains code => StatusCode.UnexpectedError
        case _: Int => successStatus
      }
      CommonResponse.empty[Boolean](status)
    })
  }

  /** #T-CRH-04. */
  /*protected def handleResponse[T <: CommonResponse[_]](response: T)(
      opName: Option[String] = Option.empty,
      sn: Option[String] = Option.empty,
      successStatus: Int)
                                            (implicit writeable: Writeable[T]): Result = {
    logFuncK[Result]("handleResponse", "T-CRH-04", response, opName, sn)(
      logApiRS(
        Result(getResponseHeader(response.statusCode, successStatus), writeable.toEntity(response))
      )(opName.getOrElse("X"), sn.getOrElse("X"))(response)
    )
  }*/

  /** #T-CRH-05. */
  protected def handleResponse[T <: CommonResponse[_]](response: T,
                                             opName: String,
                                             sn: String,
                                             successStatus: Int = OK)
                                            (implicit writeable: Writeable[T]): Result = {
    logFuncK("handleResponse", "T-CRH-05", response, successStatus, opName, sn)(
      //handleResponse(response)(Option(opName), Option(sn), successStatus)
      logApiRS(
        Result(getResponseHeader(response.statusCode, successStatus), writeable.toEntity(response))
      )(opName, sn)(response)
    )
  }

  /** #T-CRH-06. */
  protected def handleResponseByHttpStatus(statusCode: Int, successStatus: StatusCode = StatusCode.Ok): CommonResponse[Boolean] = {
    logFuncK("handleResponseByHttpStatus", "T-CRH-06", statusCode, successStatus)(
      getCommonResponseByHttpStatus(statusCode, successStatus)
    )
  }

  /** #T-CRH-07. */
  protected def createResponse[T <: CommonResponse[_]](response: T, httpStatus: Int)(implicit writeable: Writeable[T]): Result = {
    logFuncK("createResponse", "T-CRH-06", response, httpStatus)(
      Result(ResponseHeader(httpStatus), writeable.toEntity(response))
    )
  }

}
