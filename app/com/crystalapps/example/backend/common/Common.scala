package com.crystalapps.example.backend.common

import java.sql.Timestamp
import java.util.UUID

import scala.collection.generic.GenericTraversableTemplate

/**
  * Created by changyenh on 西暦18/05/05.
  */
object Common extends CommonClass {

  /** F-C-01. */
  def generateUUID: UUID = logFuncK("generateUUID", "F-C-01")(UUID.randomUUID())

  /** F-C-02. */
  def getCurrentTimestamp: Timestamp = logFuncK("getCurrentTimestamp", "F-C-02")(new Timestamp(System.currentTimeMillis()))

  /** F-C-03. */
  def isDefined[T](value: T): Boolean = logFuncK("isDefined", "F-C-03")(Option(value).isDefined)

  /** F-C-04. */
  def stringToUUID(str: String): UUID = logFuncK("stringToUUID", "F-C-04")(UUID.fromString(str))

  /** F-C-05. */
  def UUIDToString(uuid: UUID): String = logFuncK("UUIDToString", "F-C-05")(uuid.toString.toUpperCase)

  def mkQueryStringMap(params: (String, _ >: String)*): Map[String, Seq[String]] = {
    params.map(data => {
      data._1 -> (data._2 match {
        case value: String => Seq(value)
        case value: Option[String] => Seq(value.getOrElse(""))
        case value: Traversable[String] => value.toSeq
        case value => Seq(value.toString)
      })
    }).toMap
  }

  def mkQueryStringMapWithoutEmpty(params: (String, _ >: String)*): Map[String, Seq[String]] = {
    mkQueryStringMap(params:_*).filterNot(_._2.isEmpty).filterNot(_._2.forall(_.isEmpty))
  }

  implicit class StringOps(str: String) {

    def strip(stripStr: String): String = str.stripPrefix(stripStr).stripSuffix(stripStr)

  }

}
