package com.crystalapps.example.backend.common

/**
  * Created by changyenh on 西暦18/05/14.
  */
object OptionHelper {

  implicit class OptionHelper[T](obj: Option[T]) extends CommonClass {
    def ifDefined[R](defined: T => R, undefined: Option[T] => R): R = {
      if (obj.isDefined) defined.apply(obj.get) else undefined.apply(obj)
    }
  }

}
