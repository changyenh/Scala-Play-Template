package com.crystalapps.example.backend.database.dao.`trait`

import com.crystalapps.example.backend.common.FutureRecover
import com.crystalapps.example.backend.common.{CommonClass, FutureRecover}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/11.
  */
trait DaoTrait extends FutureRecover {

  /** #T-DT-01. */
  def tryCatchRecover[T](action: => Future[T],
                         map: (T) => Option[T],
                         recover: => PartialFunction[Throwable, Option[T]] = optionRecover[T])
                        (implicit executionContext: ExecutionContext): Future[Option[T]] = {
    logDaoK("tryCatchRecover", "T-DT-01")(
      action.map(res => map.apply(res)).recover(recover)
    )
  }

  /** #T-DT-02. */
  def tryRecover[T](action: => Future[Option[T]],
                    recover: => PartialFunction[Throwable, Option[T]] = optionRecover[T])
                   (implicit executionContext: ExecutionContext): Future[Option[T]] = {
    logDaoK("tryRecover", "T-DT-02")(
      action.recover(recover)
    )
  }

}
