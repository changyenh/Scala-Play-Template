package com.crystalapps.example.backend.common

import com.crystalapps.example.backend.common.CommonResponse.StatusCode.StatusCode

/**
  * Created by changyenh on 西暦18/05/08.
  */
case class CommonResponse[T](payload: Option[T], statusCode: StatusCode, message: String)

object CommonResponse {

  object StatusCode extends Enumeration(1) {
    type StatusCode = Value
    val Ok,
    UnknownError, // Common Failure
    UnexpectedError, // Recover Failure
    InsertionError,
    UpdateError,
    DeletionError,
    NotFound,
    BadRequest,
    Forbidden = Value
  }

  def unapply2[T](arg: CommonResponse[T]): Option[(Option[T], Int, String)] = {
    Option((arg.payload, arg.statusCode.id, arg.message))
  }

  private def getStatusCode(statusCode: Int, default: StatusCode = StatusCode.UnexpectedError): StatusCode = {
    StatusCode.values.find(_.id == statusCode).getOrElse(default)
  }

  def apply[T](payload: Option[T], statusCode: Int, message: String): CommonResponse[T] = this (payload, getStatusCode(statusCode), message)

  def apply[T](payload: Option[T], statusCode: StatusCode): CommonResponse[T] = this (payload, statusCode, statusCode.toString)

  def apply[T](payload: T, statusCode: StatusCode) = new CommonResponse(Option(payload), statusCode, statusCode.toString)

  def apply[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.Ok)

  def apply[T](payload: T): CommonResponse[T] = this (Option(payload))

  def empty[T](statusCode: StatusCode, message: String): CommonResponse[T] = new CommonResponse[T](Option.empty, statusCode, message)

  def empty[T](statusCode: StatusCode): CommonResponse[T] = empty[T](statusCode, statusCode.toString)

  def empty[T](statusCode: Int): CommonResponse[T] = empty[T](getStatusCode(statusCode))

  def ok[T](payload: T): CommonResponse[T] = this (payload)

  def ok[T](payload: Option[T]): CommonResponse[T] = this (payload)

  def ok[T]: CommonResponse[T] = empty[T](StatusCode.Ok)

  def unknownError[T]: CommonResponse[T] = empty[T](StatusCode.UnknownError)

  def unexpectedError[T]: CommonResponse[T] = empty(StatusCode.UnexpectedError)

  def insertionError[T]: CommonResponse[T] = empty(StatusCode.InsertionError)

  def updateError[T]: CommonResponse[T] = empty(StatusCode.UpdateError)

  def deletionError[T]: CommonResponse[T] = empty(StatusCode.DeletionError)

  def notFound[T]: CommonResponse[T] = empty(StatusCode.NotFound)

  def badRequest[T]: CommonResponse[T] = empty(StatusCode.BadRequest)

  def unknownError[T](payload: T): CommonResponse[T] = this (payload, StatusCode.UnknownError)

  def unknownError[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.UnknownError)

  def unexpectedError[T](payload: T): CommonResponse[T] = this (payload, StatusCode.UnexpectedError)

  def unexpectedError[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.UnexpectedError)

  def insertionError[T](payload: T): CommonResponse[T] = this (payload, StatusCode.InsertionError)

  def insertionError[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.InsertionError)

  def updateError[T](payload: T): CommonResponse[T] = this (payload, StatusCode.UpdateError)

  def updateError[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.UpdateError)

  def deletionError[T](payload: T): CommonResponse[T] = this (payload, StatusCode.DeletionError)

  def deletionError[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.DeletionError)

  def notFound[T](payload: T): CommonResponse[T] = this (payload, StatusCode.NotFound)

  def notFound[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.NotFound)

  def badRequest[T](payload: T): CommonResponse[T] = this (payload, StatusCode.BadRequest)

  def badRequest[T](payload: Option[T]): CommonResponse[T] = this (payload, StatusCode.BadRequest)
}