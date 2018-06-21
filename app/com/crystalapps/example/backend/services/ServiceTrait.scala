package com.crystalapps.example.backend.services

import com.crystalapps.example.backend.common.FutureRecover
import com.crystalapps.example.backend.common.{CommonResponse, FutureRecover}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/11.
  */
trait ServiceTrait extends FutureRecover {

  /** #T-ST-01. */
  def tryCatchRecover[T](action: => Future[CommonResponse[T]],
                         map: (CommonResponse[T]) => CommonResponse[T],
                         recover: => PartialFunction[Throwable, CommonResponse[_]] = responseRecover)
                        (implicit executionContext: ExecutionContext): Future[CommonResponse[_]] = {
    logServiceK("tryCatchRecover", "T-ST-01")(
      action.map(res => map.apply(res)).recover(recover)
    )
  }

  /** #T-ST-02. */
  def tryRecover[T](action: => Future[CommonResponse[T]],
                    recover: => PartialFunction[Throwable, CommonResponse[_]] = responseRecover)
                   (implicit executionContext: ExecutionContext): Future[CommonResponse[_]] = {
    logServiceK("tryRecover", "T-ST-02")(
      action.recover(recover)
    )
  }

}
