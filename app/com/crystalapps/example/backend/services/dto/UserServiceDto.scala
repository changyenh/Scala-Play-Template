package com.crystalapps.example.backend.services.dto

import com.crystalapps.example.backend.services.dto.BasicServiceDto.{CommonDtoFields, CommonDtoModel}
import com.crystalapps.example.backend.database.models.UserModels.UserModel
import com.crystalapps.example.backend.services.dto.BasicServiceDto.{CommonDtoFields, CommonDtoModel}

/**
  * Created by changyenh on 西暦18/05/12.
  */
object UserServiceDto {

  case class UserDto(username: Option[String], name: Option[String], commonDtoFields: CommonDtoFields)
      extends CommonDtoModel

  object UserDto {
    val transformerUD2UM: DtoTransformer[UserDto, UserModel] = new DtoTransformer[UserDto, UserModel] {
      override def from(model: UserModel): UserDto = {
        UserDto(
          Option(model.username),
          Option(model.name),
          CommonDtoFields.from(model.commonFields)
        )
      }

      override def to(dto: UserDto): UserModel = {
        UserModel(
          dto.username.orNull,
          null,
          dto.name.orNull,
          CommonDtoFields.to(dto.commonDtoFields)
        )
      }
    }

  }

}
