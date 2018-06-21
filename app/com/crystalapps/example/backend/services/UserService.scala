package com.crystalapps.example.backend.services

import java.util.UUID

import com.crystalapps.example.backend.common.{CommonResponse, JsonConversions}
import javax.inject.Inject
import com.crystalapps.example.backend.database.dao.UserDao
import com.crystalapps.example.backend.database.models.UserModels._
import com.crystalapps.example.backend.services.dto.LoginServiceDto.LoginDto
import com.crystalapps.example.backend.services.dto.UserServiceDto.UserDto

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/09.
  */
class UserService @Inject()(dao: UserDao)(implicit executionContext: ExecutionContext)
    extends BasicService[UserModel, UserVo, UserDao, UserDto](dao)(executionContext, UserDto.transformerUD2UM) {

  import JsonConversions._

  /*def find(id: String): Future[CommonResponse[_]] = {
      super.find(id)(UserVo().setId(id))
  }

  def remove(id: String): Future[CommonResponse[_]] = {
      super.remove(id)(UserVo().setId(id))
  }*/

  def findByVoData(username: Option[String] = Option.empty,
                   password: Option[String] = Option.empty,
                   name: Option[String] = Option.empty,
                   id: Option[UUID] = Option.empty): Future[Option[UserModel]] = {
    logServiceK("findByVoData", "", name, id)(
      dao.find(UserVo(username, password, name).setId(id))
    )
  }

  def login(loginDto: LoginDto): Future[Option[UserModel]] = {
    logServiceK("login", "", loginDto)({
      val user = findByVoData(Some(loginDto.username), Some(loginDto.password))
      user.map(_.map(loggedInProcess))
    })
  }

  def loggedInProcess(user: UserModel): UserModel = user

  def loginAuth(username: String, password: String): Future[Option[UserModel]] = {
    logServiceK("loginAuth", "", username, password)(
      login(LoginDto(username, password))
    )
  }

  def loginDirect(loginDto: LoginDto): Future[CommonResponse[UserModel]] = {
    logServiceK("loginDirect", "", loginDto)(
      login(loginDto).map(resultOpt =>
        resultOpt.map(result =>
          CommonResponse.ok(result)
        ).getOrElse(
          CommonResponse.notFound[UserModel]
        )
      )
    ).recover(responseRecover)
  }

}
