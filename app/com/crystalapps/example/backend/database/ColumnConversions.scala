package com.crystalapps.example.backend.database

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType

/**
  * Created by changyenh on 西暦18/05/14.
  */
object ColumnConversions {

  import DefaultProfile.MappedColumnType
  import DefaultProfile.api._

  implicit val stringSeqMapper: JdbcType[Seq[String]] with BaseTypedType[Seq[String]] = MappedColumnType.base[Seq[String],String](
    seq => seq.mkString(","),
    string => string.split(',').map(_.trim).toList
  )

}
