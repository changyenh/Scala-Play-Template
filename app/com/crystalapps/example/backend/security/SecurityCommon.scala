package com.crystalapps.example.backend.security

import java.security.{MessageDigest, SecureRandom, Security}
import java.util.{Base64, Date}

import com.crystalapps.example.backend.database.models.SecurityModels.OAuthAccessTokenModel
import com.crystalapps.example.backend.common.Common
import com.crystalapps.example.backend.database.models.SecurityModels.OAuthAccessTokenModel
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex

import scala.util.Random
import scalaoauth2.provider.AccessToken

/**
  * Created by changyenh on 西暦18/05/14.
  */
object SecurityCommon {

  private val accessTokenExpireSeconds: Int = 1800
  private val defaultCharset: String = "UTF-8"
  private val codeVerifierLength = 60 //minimum 43.

  // register the BouncyCastleProvider with the Security Manager// register the BouncyCastleProvider with the Security Manager

  Security.addProvider(new BouncyCastleProvider)
  private val secureRandom: SecureRandom = new SecureRandom()
  private val digest256: MessageDigest = MessageDigest.getInstance("SHA3-256")
  private val digest384: MessageDigest = MessageDigest.getInstance("SHA3-384")

  def randomString(length: Int): String = new Random(secureRandom).alphanumeric.take(length).mkString

  def base64Encode(str: String): String = Base64.getEncoder.encodeToString(str.getBytes(defaultCharset))

  def base64Decode(str: String): String = new String(Base64.getDecoder.decode(str), defaultCharset)

  def base64UrlEncode(str: String): String = Base64.getUrlEncoder.encodeToString(str.getBytes(defaultCharset))

  def base64UrlDecode(str: String): String = new String(Base64.getUrlDecoder.decode(str), defaultCharset)

  def sha3_256(str: String): String = Hex.toHexString(digest256.digest(str.getBytes(defaultCharset)))

  def sha3_384(str: String): String = Hex.toHexString(digest384.digest(str.getBytes(defaultCharset)))

  def generateToken: String = base64Encode(Common.generateUUID.toString.toUpperCase)

  def hashPassword(password: String): String = sha3_256(password)

  def generateCodeVerifier: String = randomString(codeVerifierLength)

  def hashCodeVerifier(codeVerifier: String): String = base64UrlEncode(sha3_256(codeVerifier))

  def generateCodeVerifierChallengePair: (String, String) = {
    val code = generateCodeVerifier
    (code, hashCodeVerifier(code))
  }

  def toAccessToken(token: OAuthAccessTokenModel): AccessToken = {
    AccessToken(token.accessToken,
      token.refreshToken,
      token.getScopeStringOption,
      Some(accessTokenExpireSeconds),
      new Date(token.createdAt.getTime)
    )
  }

}
