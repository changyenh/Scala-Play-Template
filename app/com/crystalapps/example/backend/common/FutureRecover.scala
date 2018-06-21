package com.crystalapps.example.backend.common

import play.api.mvc.Result

/**
  * Created by changyenh on 西暦18/05/15.
  */
trait FutureRecover extends CommonResponseHandler {

  import JsonConversions._

  def recoverError[T](t: Throwable, default: T): T = {
    logFuncK("recoverError", "T-FR-01", t.getMessage)({
      error(t)
      default
    })
  }

  /** #T-FR-02. */
  protected def responseRecover[T]: PartialFunction[Throwable, CommonResponse[T]]
  = new PartialFunction[Throwable, CommonResponse[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): CommonResponse[T] = {
      logFuncK("optionRecover", "T-FR-02", t.getMessage)(
        recoverError(t, CommonResponse.unexpectedError[T])
      )
    }
  }

  /** #T-FR-03. */
  protected def optionRecover[T]: PartialFunction[Throwable, Option[T]]
  = new PartialFunction[Throwable, Option[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Option[T] = {
      logFuncK("optionRecover", "T-FR-03", t.getMessage)(
        recoverError(t, Option.empty[T])
      )
    }
  }

  /** #T-FR-04. */
  protected def seqRecover[T]: PartialFunction[Throwable, Seq[T]]
  = new PartialFunction[Throwable, Seq[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Seq[T] = {
      logFuncK("seqRecover", "T-FR-04", t.getMessage)(
        recoverError(t, Seq.empty[T])
      )
    }
  }

  /** #T-FR-05. */
  protected def responseRecover[T](default: CommonResponse[T]): PartialFunction[Throwable, CommonResponse[T]]
  = new PartialFunction[Throwable, CommonResponse[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): CommonResponse[T] = {
      logFuncK("optionRecover", "T-FR-05", t.getMessage)(
        recoverError(t, default)
      )
    }
  }

  /** #T-FR-06. */
  protected def optionRecover[T](default: T): PartialFunction[Throwable, Option[T]]
  = new PartialFunction[Throwable, Option[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Option[T] = {
      logFuncK("optionRecover", "T-FR-06", t.getMessage)(
        recoverError(t, Option(default))
      )
    }
  }

  /** #T-FR-07. */
  protected def seqRecover[T](default: Seq[T]): PartialFunction[Throwable, Seq[T]]
  = new PartialFunction[Throwable, Seq[T]] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Seq[T] = {
      logFuncK("seqRecover", "T-FR-07", t.getMessage)(
        recoverError(t, default)
      )
    }
  }

  /** #T-FR-08. */
  protected def resultRecover: PartialFunction[Throwable, Result]
  = new PartialFunction[Throwable, Result] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Result = {
      logFuncK("resultRecover", "T-FR-08", t.getMessage)(
        recoverError(t, handleResponse(
          CommonResponse.unexpectedError[Boolean],
          "resultRecover", "T-FR-08"
        ))
      )
    }
  }

  /*/** #T-FR-09. */
  protected def resultRecover[_](default: CommonResponse[_]): PartialFunction[Throwable, Result]
  = new PartialFunction[Throwable, Result] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Result = {
      logFuncK("resultRecover", "T-FR-08", t.getMessage)(
        recoverError(t, handleResponse(default, "resultRecover", "T-FR-08"))
      )
    }
  }*/

  /** #T-FR-10. */
  protected def resultRecover(default: Result): PartialFunction[Throwable, Result]
  = new PartialFunction[Throwable, Result] {
    override def isDefinedAt(t: Throwable): Boolean = t.isInstanceOf[Exception]

    override def apply(t: Throwable): Result = {
      logFuncK("resultRecover", "T-FR-08", t.getMessage)(
        recoverError(t, default)
      )
    }
  }

}
