package com.crystalapps.example.backend.services.dto

import play.api.libs.json.{Json, OFormat}

/**
  * Created by changyenh on 西暦18/05/17.
  */
object LoginServiceDto {

  case class LoginDto(username: String, password: String, redirectUri: Option[String] = Option.empty)

  object LoginDto {
    implicit val loginDtoFormat: OFormat[LoginDto] = Json.format[LoginDto]
  }

}
