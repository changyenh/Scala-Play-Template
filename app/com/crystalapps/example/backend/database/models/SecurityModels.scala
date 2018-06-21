package com.crystalapps.example.backend.database.models

import java.sql.Timestamp
import java.util.UUID

import com.crystalapps.example.backend.common.Common

/**
  * Created by changyenh on 西暦18/05/13.
  */
object SecurityModels {

  import com.crystalapps.example.backend.database.ColumnConversions._
  import com.crystalapps.example.backend.database.DefaultProfile.api._

  //case class OAuthGrantTypeModel(grantId: Long, grantType: String)

  case class OAuthClientModel(clientId: UUID,
                              clientSecret: String,
                              userId: UUID,
                              redirectUri: Option[String],
                              scope: Seq[String],
                              grantType: String,
                              createdAt: Timestamp)

  case class OAuthAuthorizationCodeModel(key: Long,
                                         userId: UUID,
                                         clientId: UUID,
                                         authCode: String,
                                         redirectUri: Option[String],
                                         createdAt: Timestamp)

  case class OAuthAccessTokenModel(key: Long,
                                   userId: UUID,
                                   clientId: UUID,
                                   accessToken: String,
                                   refreshToken: Option[String],
                                   scope: Seq[String],
                                   createdAt: Timestamp) {
    def getScopeString: String = scope.map(_.trim).mkString(",")

    def getScopeStringOption: Option[String] = {
      val scopeString = getScopeString
      if (scopeString.nonEmpty) Option(scopeString) else Option.empty
    }
  }

  //case class OAuthGrantTypeVo(grantId: Option[Long] = Option.empty, grantType: Option[String] = Option.empty)

  case class OAuthClientVo(clientId: Option[UUID] = Option.empty,
                           clientSecret: Option[String] = Option.empty,
                           redirectUri: Option[String] = Option.empty,
                           grantType: Option[String] = Option.empty,
                           userId: Option[UUID] = Option.empty)

  case class OAuthAuthorizationCodeVo(key: Option[Long] = Option.empty,
                                      userId: Option[UUID] = Option.empty,
                                      clientId: Option[UUID] = Option.empty,
                                      authCode: Option[String] = Option.empty)

  case class OAuthAccessTokenVo(key: Option[Long] = Option.empty,
                                userId: Option[UUID] = Option.empty,
                                clientId: Option[UUID] = Option.empty,
                                accessToken: Option[String] = Option.empty,
                                refreshToken: Option[String] = Option.empty)


  /*class OAuthGrantTypeTable(tag: Tag) extends Table[OAuthGrantTypeModel](tag, "oauth_grant_type") {

    def grantId = column[Long]("grant_id", O.PrimaryKey, O.AutoInc)

    def grantType = column[String]("grant_type", O.Length(50))

    def * = (grantId, grantType) <> ((OAuthGrantTypeModel.apply _).tupled, OAuthGrantTypeModel.unapply)
  }*/

  class OAuthClientTable(tag: Tag) extends Table[OAuthClientModel](tag, "oauth_client") {

    def clientId = column[UUID]("client_id", O.PrimaryKey)

    def clientSecret = column[String]("client_secret", O.Length(32))

    def userId = column[UUID]("user_id")

    def redirectUri = column[Option[String]]("redirect_uri")

    def scope = column[Seq[String]]("scope")

    def grantType = column[String]("grant_type", O.Length(50))

    def createdAt = column[Timestamp]("created_at", O.Default(Common.getCurrentTimestamp))

    def * = (clientId, clientSecret, userId, redirectUri, scope, grantType, createdAt) <> ((OAuthClientModel.apply _).tupled, OAuthClientModel.unapply)
  }

  class OAuthAuthorizationCodeTable(tag: Tag) extends Table[OAuthAuthorizationCodeModel](tag, "oauth_authorization_code") {
    def key = column[Long]("key", O.PrimaryKey, O.AutoInc)

    def userId = column[UUID]("user_id")

    def clientId = column[UUID]("client_id")

    def authCode = column[String]("auth_code", O.Unique)

    def redirectUri = column[Option[String]]("redirect_uri")

    def createdAt = column[Timestamp]("created_at", O.Default(Common.getCurrentTimestamp))

    def * = (key, userId, clientId, authCode, redirectUri, createdAt) <> ((OAuthAuthorizationCodeModel.apply _).tupled, OAuthAuthorizationCodeModel.unapply)

  }

  class OAuthAccessTokenTable(tag: Tag) extends Table[OAuthAccessTokenModel](tag, "oauth_access_token") {
    def key = column[Long]("key", O.PrimaryKey, O.AutoInc)

    def userId = column[UUID]("user_id")

    def clientId = column[UUID]("client_id")

    def accessToken = column[String]("access_token")

    def refreshToken = column[Option[String]]("refresh_token")

    def scope = column[Seq[String]]("scope")

    def createdAt = column[Timestamp]("created_at", O.Default(Common.getCurrentTimestamp))

    def * = (key, userId, clientId, accessToken, refreshToken, scope, createdAt) <> ((OAuthAccessTokenModel.apply _).tupled, OAuthAccessTokenModel.unapply)
  }

}
