package com.crystalapps.example.backend.database.models

import com.crystalapps.example.backend.database.models.CommonModels.{CommonFields, CommonModel, CommonVo}
import com.crystalapps.example.backend.database.models.CommonModels.{CommonFields, CommonModel, CommonVo}

/**
  * Created by changyenh on 西暦18/05/08.
  */
object UserModels {

  case class UserModel(username: String,
                       password: String,
                       name: String,
                       commonFields: CommonFields = CommonFields())
      extends CommonModel

  object UserModel {
    //        implicit val userReads: Reads[UserModel] = commonReads(Json.reads[UserModel])
    //        implicit val userWrites: Writes[UserModel] = commonWrites(Json.writes[UserModel])
  }

  case class UserVo(username: Option[String] = Option.empty,
                    password: Option[String] = Option.empty,
                    name: Option[String] = Option.empty) extends CommonVo

}
