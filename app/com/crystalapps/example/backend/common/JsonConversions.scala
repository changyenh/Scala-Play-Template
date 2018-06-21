package com.crystalapps.example.backend.common

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.crystalapps.example.backend.database.models.BasicModels.BasicFields
import com.crystalapps.example.backend.database.models.CommonModels.CommonFields
import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel
import com.crystalapps.example.backend.services.dto.BasicServiceDto.{BasicDtoFields, CommonDtoFields}
import com.crystalapps.example.backend.services.dto.ServiceSettingServiceDto.ServiceSettingDto
import com.crystalapps.example.backend.services.dto.UserServiceDto.UserDto
import com.crystalapps.example.backend.database.models.BasicModels.BasicFields
import com.crystalapps.example.backend.database.models.CommonModels.CommonFields
import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel
import com.crystalapps.example.backend.database.models.UserModels.UserModel
import play.api.http.{ContentTypes, Writeable}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc.Codec
import com.crystalapps.example.backend.services.dto.BasicServiceDto.{BasicDtoFields, CommonDtoFields}
import com.crystalapps.example.backend.services.dto.ServiceSettingServiceDto.ServiceSettingDto
import com.crystalapps.example.backend.services.dto.UserServiceDto.UserDto

/**
  * Created by changyenh on 西暦18/05/07.
  */
object JsonConversions {

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    def reads(json: JsValue): JsSuccess[Timestamp] = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }

    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val basicFieldsFormat: OFormat[BasicFields] = Json.format[BasicFields]
  implicit val commonFieldsFormat: OFormat[CommonFields] = Json.format[CommonFields]

  private val basicFieldsName = "basicFields"
  private val commonFieldsName = "commonFields"
  private val basicFields = Vector("createdAt", "updatedAt", "active")
  private val commonFields = Vector("key", "id") ++ basicFields

  implicit val basicDtoFieldsFormat: OFormat[BasicDtoFields] = Json.format[BasicDtoFields]
  implicit val commonDtoFieldsFormat: OFormat[CommonDtoFields] = Json.format[CommonDtoFields]

  private val basicDtoFieldsName = "basicDtoFields"
  private val commonDtoFieldsName = "commonDtoFields"
  private val basicDtoFields = Vector("createdAt", "updatedAt")
  private val commonDtoFields = Vector("id") ++ basicDtoFields

  def addReadsTransformer(key: String, fields: String*): Reads[JsObject] = {
    __.json.update(
      __.read[JsObject].map { js: JsObject => {
        val jsKeys = js.keys
        val added = fields.foldLeft(JsObject.empty)((previous, current) => {
          if (jsKeys.contains(current))
            previous ++ Json.obj(current -> (js \ current).get)
          else
            previous
        })
        val removed = fields.foldLeft(js)((previous, current) => {
          previous - current
        })
        removed ++ Json.obj(key -> added)
      }
      }
    )
  }

  def addWritesTransformer(js: JsObject, key: String, fields: String*): JsObject = {
    val child = (js \ key).get
    val added = fields.foldLeft(js.as[JsObject])((previous, current) => {
      previous ++ Json.obj(current -> (child \ current).toOption)
    })
    added - key
  }

  // Model
  def basicFieldsWritesTransformer(js: JsObject): JsObject = addWritesTransformer(js, basicFieldsName, basicFields: _*)

  def basicFieldsReadsTransformer: Reads[JsObject] = addReadsTransformer(basicFieldsName, basicFields: _*)

  def commonFieldsWritesTransformer(js: JsObject): JsObject = addWritesTransformer(js, commonFieldsName, commonFields: _*)

  def commonFieldsReadsTransformer: Reads[JsObject] = addReadsTransformer(commonFieldsName, commonFields: _*)

  def basicWrites[T](writes: OWrites[T]): Writes[T] = writes.transform((js: JsObject) => basicFieldsWritesTransformer(js))

  def basicReads[T](reads: Reads[T]): Reads[T] = basicFieldsReadsTransformer.andThen(reads)

  def commonWrites[T](writes: OWrites[T]): Writes[T] = writes.transform((js: JsObject) => commonFieldsWritesTransformer(js))

  def commonReads[T](reads: Reads[T]): Reads[T] = commonFieldsReadsTransformer.andThen(reads)

  // Dto Basic/Common
  def basicDtoFieldsWritesTransformer(js: JsObject): JsObject = addWritesTransformer(js, basicDtoFieldsName, basicDtoFields: _*)

  def basicDtoFieldsReadsTransformer: Reads[JsObject] = addReadsTransformer(basicDtoFieldsName, basicDtoFields: _*)

  def commonDtoFieldsWritesTransformer(js: JsObject): JsObject = addWritesTransformer(js, commonDtoFieldsName, commonDtoFields: _*)

  def commonDtoFieldsReadsTransformer: Reads[JsObject] = addReadsTransformer(commonDtoFieldsName, commonDtoFields: _*)

  def basicDtoWrites[T](writes: OWrites[T]): Writes[T] = writes.transform((js: JsObject) => basicDtoFieldsWritesTransformer(js))

  def basicDtoReads[T](reads: Reads[T]): Reads[T] = basicDtoFieldsReadsTransformer.andThen(reads)

  def commonDtoWrites[T](writes: OWrites[T]): Writes[T] = writes.transform((js: JsObject) => commonDtoFieldsWritesTransformer(js))

  def commonDtoReads[T](reads: Reads[T]): Reads[T] = commonDtoFieldsReadsTransformer.andThen(reads)

  // Models

  //    import ServiceSettingModel._
  //    import UserModel._
  implicit val serviceSettingReads: Reads[ServiceSettingModel] = basicReads(Json.reads[ServiceSettingModel])
  implicit val serviceSettingWrites: Writes[ServiceSettingModel] = basicWrites(Json.writes[ServiceSettingModel])
  implicit val userReads: Reads[UserModel] = commonReads(Json.reads[UserModel])
  implicit val userWrites: Writes[UserModel] = commonWrites(Json.writes[UserModel])

  // Dto Models
  implicit val serviceSettingDtoReads: Reads[ServiceSettingDto] = basicDtoReads(Json.reads[ServiceSettingDto])
  implicit val serviceSettingDtoWrites: Writes[ServiceSettingDto] = basicDtoWrites(Json.writes[ServiceSettingDto])
  implicit val userDtoReads: Reads[UserDto] = commonDtoReads(Json.reads[UserDto])
  implicit val userDtoWrites: Writes[UserDto] = commonDtoWrites(Json.writes[UserDto])

  // CommonResponse

  implicit def commonResponseWrites[T](implicit nested: Writes[T]): Writes[CommonResponse[T]] =
    (
        (__ \ "statusCode").write[Int] and
            (__ \ "message").write[String] and
            (__ \ "payload").writeNullable[T]
        ) (p => (p.statusCode.id, p.message, p.payload))

  //        ) (unlift(CommonResponse.unapply2[T]))

  /*implicit def commonResponseReads[T]: Reads[CommonResponse[T]] = (
          (__ \ "statusCode").read[Int] and
                  (__ \ "message").read[String] and
                  (__ \ "payload").readNullable[T]
          ).tupled.map(data => CommonResponse.apply(data._3, data._1, data._2))*/

  implicit def commonResponseWriteable[T](implicit codec: Codec, writes: Writes[T]): Writeable[CommonResponse[T]] = {
    Writeable(data => codec.encode(Json.stringify(Json.toJson(data))), Some(ContentTypes.JSON))
  }

}
