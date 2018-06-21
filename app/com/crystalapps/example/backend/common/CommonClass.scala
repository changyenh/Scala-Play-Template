package com.crystalapps.example.backend.common

import logging.ProjectLogger

/**
  * Created by changyenh on 西暦18/05/11.
  */
trait CommonClass extends ProjectLogger {

  /** Process and return the original value. */
  protected def kestrel[T](x: T)(f: T => Unit): T = {
    f(x)
    x
  }

  /** Log Error level parameters with custom prefix. */
  protected def logParamsError(prefix: String, params: Any*): Unit = {
    error(params.mkString(s"~$prefix: (", ", ", ")"))
  }

  /** Log Warn level parameters with custom prefix. */
  protected def logParamsWarn(prefix: String, params: Any*): Unit = {
    warn(params.mkString(s"~$prefix: (", ", ", ")"))
  }

  /** Log Info level parameters with custom prefix. */
  protected def logParamsInfo(prefix: String, params: Any*): Unit = {
    info(params.mkString(s"~$prefix: (", ", ", ")"))
  }

  /** Log Debug level parameters with custom prefix. */
  protected def logParamsDebug(prefix: String, params: Any*): Unit = {
    debug(params.mkString(s"~$prefix: (", ", ", ")"))
  }

  /** Log Default(Info) level parameters with custom prefix. */
  protected def logParams(prefix: String, params: Any*): Unit = logParamsInfo(prefix, params: _*)

  /** Log operation name, serial number and parameters with class level prefix for entering operations. */
  protected def logEnter(level: String, opName: String, sn: String, params: Any*): Unit = {
    logParams(s"${level}_Enter-#${sn}_$opName", params: _*)
  }

  /** Log operation name, serial number and parameters with class level prefix for leaving operations. */
  protected def logReturn(level: String, opName: String, sn: String, params: Any*): Unit = {
    logParams(s"${level}_Return-#${sn}_$opName", params: _*)
  }

  /** Log operation name, serial number and parameters in Api level for entering operations. */
  protected def logApiE(opName: String, sn: String, params: Any*): Unit = {
    logEnter("Api", opName, sn, params: _*)
  }

  /** Log operation name, serial number and parameters in Service level for entering operations. */
  protected def logServiceE(opName: String, sn: String, params: Any*): Unit = {
    logEnter("Service", opName, sn, params: _*)
  }

  /** Log operation name, serial number and parameters in Dao level for entering operations. */
  protected def logDaoE(opName: String, sn: String, params: Any*): Unit = {
    logEnter("Dao", opName, sn, params: _*)
  }

  /** Log operation name, serial number and parameters in Function level for entering operations. */
  protected def logFuncE(opName: String, sn: String, params: Any*): Unit = {
    logEnter("Func", opName, sn, params: _*)
  }

  /** Log operation name, serial number and return value in Api level for leaving operations. */
  protected def logApiR[T](opName: String, sn: String, value: T): Unit = {
    logReturn("Api", opName, sn, value)
  }

  /** Log operation name, serial number and return value in Service level for leaving operations. */
  protected def logServiceR[T](opName: String, sn: String, value: T): Unit = {
    logReturn("Service", opName, sn, value)
  }

  /** Log operation name, serial number and return value in Dao level for leaving operations. */
  protected def logDaoR[T](opName: String, sn: String, value: T): Unit = {
    logReturn("Dao", opName, sn, value)
  }

  /** Log operation name, serial number and return value in Function level for leaving operations. */
  protected def logFuncR[T](opName: String, sn: String, value: T): Unit = {
    logReturn("Func", opName, sn, value)
  }

  /** Return the value after logging through [[logApiR]] by using [[kestrel]]. */
  protected def logApiRK[T](opName: String, sn: String, value: T): T = {
    kestrel(value)(logApiR(opName, sn, _))
  }

  /** Return the value after logging through [[logServiceR]] by using [[kestrel]]. */
  protected def logServiceRK[T](opName: String, sn: String, value: T): T = {
    kestrel(value)(logServiceR(opName, sn, _))
  }

  /** Return the value after logging through [[logDaoR]] by using [[kestrel]]. */
  protected def logDaoRK[T](opName: String, sn: String, value: T): T = {
    kestrel(value)(logDaoR(opName, sn, _))
  }

  /** Return the value after logging through [[logFuncR]] by using [[kestrel]]. */
  protected def logFuncRK[T](opName: String, sn: String, value: T): T = {
    kestrel(value)(logFuncR(opName, sn, _))
  }

  /** Log before evaluating {@code action} using [[logApiE]] and return the result after logging through [[logApiRK]]. */
  protected def logApiK[S](opName: String, sn: String, params: Any*)(action: => S): S = {
    logApiE(opName, sn, params: _*)
    logApiRK[S](opName, sn, action)
  }

  /** Log before evaluating {@code action} using [[logServiceE]] and return the result after logging through [[logServiceRK]]. */
  protected def logServiceK[S](opName: String, sn: String, params: Any*)(action: => S): S = {
    logServiceE(opName, sn, params: _*)
    logServiceRK[S](opName, sn, action)
  }

  /** Log before evaluating {@code action} using [[logDaoE]] and return the result after logging through [[logDaoRK]]. */
  protected def logDaoK[S](opName: String, sn: String, params: Any*)(action: => S): S = {
    logDaoE(opName, sn, params: _*)
    logDaoRK[S](opName, sn, action)
  }

  /** Log before evaluating {@code action} using [[logFuncE]] and return the result after logging through [[logFuncRK]]. */
  protected def logFuncK[S](opName: String, sn: String, params: Any*)(action: => S): S = {
    logFuncE(opName, sn, params: _*)
    logFuncRK[S](opName, sn, action)
  }

  /** Evaluate {@code action} first, then log through [[logApiR]] with specific {@code value: T} and return {@code action} result. */
  protected def logApiRS[S, T](action: => S)(opName: String, sn: String)(value: T): S = {
    val eval = action
    logApiR(opName, sn, value)
    eval
  }

  /** Evaluate {@code action} first, then log through [[logServiceR]] with specific {@code value: T} and return {@code action} result. */
  protected def logServiceRS[S, T](action: => S)(opName: String, sn: String)(value: T): S = {
    val eval = action
    logServiceR(opName, sn, value)
    eval
  }

  /** Evaluate {@code action} first, then log through [[logDaoR]] with specific {@code value: T} and return {@code action} result. */
  protected def logDaoRS[S, T](action: => S)(opName: String, sn: String)(value: T): S = {
    val eval = action
    logDaoR(opName, sn, value)
    eval
  }

  /** Evaluate {@code action} first, then log through [[logFuncR]] with specific {@code value: T} and return {@code action} result. */
  protected def logFuncRS[S, T](action: => S)(opName: String, sn: String)(value: T): S = {
    val eval = action
    logFuncR(opName, sn, value)
    eval
  }

  /** Log before evaluating {@code action} using [[logApiE]] and return the result after logging through [[logApiRS]] with specific {@code value: T}. */
  protected def logApiS[S, T](opName: String, sn: String, params: Any*)(action: => S)(value: T): S = {
    logApiE(opName, sn, params: _*)
    logApiRS(action)(opName, sn)(value)
  }

  /** Log before evaluating {@code action} using [[logServiceE]] and return the result after logging through [[logServiceRS]] with specific {@code value: T}. */
  protected def logServiceS[S, T](opName: String, sn: String, params: Any*)(action: => S)(value: T): S = {
    logServiceE(opName, sn, params: _*)
    logServiceRS(action)(opName, sn)(value)
  }

  /** Log before evaluating {@code action} using [[logDaoE]] and return the result after logging through [[logDaoRS]] with specific {@code value: T}. */
  protected def logDaoS[S, T](opName: String, sn: String, params: Any*)(action: => S)(value: T): S = {
    logDaoE(opName, sn, params: _*)
    logDaoRS(action)(opName, sn)(value)
  }

  /** Log before evaluating {@code action} using [[logFuncE]] and return the result after logging through [[logFuncRS]] with specific {@code value: T}. */
  protected def logFuncS[S, T](opName: String, sn: String, params: Any*)(action: => S)(value: T): S = {
    logFuncE(opName, sn, params: _*)
    logFuncRS(action)(opName, sn)(value)
  }

  /** Log before evaluating {@code action} using [[logApiE]] and return the result after logging through [[logApiRK]].
    * Passes {@code opName} and {@code sn} into {@code action}.
    * */
  protected def logApiKOS[S](opName: String, sn: String, params: Any*)(action:(String, String) => S): S = {
    logApiE(opName, sn, params: _*)
    logApiRK[S](opName, sn, action.apply(opName, sn))
  }

  /** Log before evaluating {@code action} using [[logServiceE]] and return the result after logging through [[logServiceRK]].
    * Passes {@code opName} and {@code sn} into {@code action}.
    * */
  protected def logServiceKOS[S](opName: String, sn: String, params: Any*)(action:(String, String) => S): S = {
    logServiceE(opName, sn, params: _*)
    logServiceRK[S](opName, sn, action.apply(opName, sn))
  }

  /** Log before evaluating {@code action} using [[logDaoE]] and return the result after logging through [[logDaoRK]].
    * Passes {@code opName} and {@code sn} into {@code action}.
    * */
  protected def logDaoKOS[S](opName: String, sn: String, params: Any*)(action:(String, String) => S): S = {
    logDaoE(opName, sn, params: _*)
    logDaoRK[S](opName, sn, action.apply(opName, sn))
  }

  /** Log before evaluating {@code action} using [[logFuncE]] and return the result after logging through [[logFuncRK]].
    * Passes {@code opName} and {@code sn} into {@code action}.
    * */
  protected def logFuncKOS[S](opName: String, sn: String, params: Any*)(action:(String, String) => S): S = {
    logFuncE(opName, sn, params: _*)
    logFuncRK[S](opName, sn, action(opName, sn))
  }

}
