package com.crystalapps.example.backend.common.logging

import grizzled.slf4j.Logging

/**
  * Created by changyenh on 西暦18/05/04.
  */
trait ProjectLogger extends Logging {

  /*def error(msg: String): Unit = {
      println(msg)
  }

  def warn(msg: String): Unit = {
      println(msg)
  }

  def info(msg: String): Unit = {
      println(msg)
  }

  def debug(msg: String): Unit = {
      println(msg)
  }*/

  protected def error(t: Throwable): Unit = {
    error("error-slf4j", t)
    //error(getStackTrace(t))
  }

  protected def warn(t: Throwable): Unit = {
    warn("warn-slf4j", t)
    //warn(getStackTrace(t))
  }

  protected def info(t: Throwable): Unit = {
    info("warn-slf4j", t)
    //info(getStackTrace(t))
  }

  protected def debug(t: Throwable): Unit = {
    debug("warn-slf4j", t)
    //debug(getStackTrace(t))
  }

  protected def getStackTrace(t: Throwable): String = {
    t.getStackTrace.mkString("\n")
  }

}
