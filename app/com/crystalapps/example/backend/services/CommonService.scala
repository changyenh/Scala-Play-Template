package com.crystalapps.example.backend.services

import com.crystalapps.example.backend.database.dao.CommonDao
import com.crystalapps.example.backend.database.models.CommonModels.{CommonModel, CommonVo}
import com.crystalapps.example.backend.services.dto.BasicServiceDto.CommonDtoModel
import com.crystalapps.example.backend.services.dto.DtoTransformer

import scala.concurrent.ExecutionContext

/**
  * Created by changyenh on 西暦18/05/09.
  */
abstract class CommonService[M <: CommonModel, V <: CommonVo, D <: CommonDao[M, V], T <: CommonDtoModel]
(dao: D)(implicit executionContext: ExecutionContext, transformer: DtoTransformer[T, M])
    extends BasicService[M, V, D, T](dao)(executionContext, transformer) {

  /*def getDao: D = dao

  def all: Future[CommonResponse[Seq[M]]] = {
      dao.all().map(result => {
          if (isDefined(result)) {
              CommonResponse.ok(result)
          } else {
              CommonResponse.unexpectedError(Seq.empty[M])
          }
      })
  }

  def insert(model: M): Future[CommonResponse[M]] = {
      dao.insert(model).map(result => {
          if (Option(result).isDefined && result > 0) {
              CommonResponse.ok(model)
          } else {
              CommonResponse.insertionError(model)
          }
      })
  }

  def update(model: M): Future[CommonResponse[M]] = {
      dao.update(model).map(result => {
          if (isDefined(result) && result > 0) {
              CommonResponse.ok(model)
          } else {
              CommonResponse.updateError(model)
          }
      })
  }

  def find(id: String)(implicit vo: V): Future[CommonResponse[_]] = {
      dao.find(vo).map(result => {
          if (isDefined(result)) {
              if (result.isDefined) {
                  CommonResponse.ok(result)
              } else {
                  CommonResponse.notFound(result)
              }
          } else {
              CommonResponse.unexpectedError
          }
      })
  }


  def remove(id: String)(implicit vo: V): Future[CommonResponse[_]] = {
      dao.remove(vo).map(result => {
          if (isDefined(result)) {
              if (result > 0) {
                  CommonResponse.ok(result)
              } else {
                  CommonResponse.notFound
              }
          } else {
              CommonResponse.unexpectedError
          }
      })
  }*/

}
