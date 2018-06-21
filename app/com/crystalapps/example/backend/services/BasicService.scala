package com.crystalapps.example.backend.services

import com.crystalapps.example.backend.common.CommonResponse
import com.crystalapps.example.backend.database.dao.BasicDao
import com.crystalapps.example.backend.database.models.BasicModels.{BasicModel, BasicVo}
import com.crystalapps.example.backend.services.dto.BasicServiceDto.BasicDtoModel
import com.crystalapps.example.backend.services.dto.DtoTransformer

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by changyenh on 西暦18/05/09.
  */
abstract class BasicService[M <: BasicModel, V <: BasicVo, D <: BasicDao[M, V], T <: BasicDtoModel]
(dao: D)
(implicit executionContext: ExecutionContext, transformer: DtoTransformer[T, M])
    extends ServiceTrait {

  /** #S-BS-01. */
  def all: Future[CommonResponse[Seq[M]]] = {
    logServiceK("all", "S-BS-01")(
      dao.all().map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(result)
        ).getOrElse(
          CommonResponse.unknownError(Seq.empty[M])
        )
      }).recover(responseRecover[Seq[M]])
    )
  }

  /** #S-BS-02. */
  def insert(model: M): Future[CommonResponse[M]] = {
    logServiceK("insert", "S-BS-02", model)(
      dao.insert(model).map(resultOpt => {
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(model)
          } else {
            CommonResponse.insertionError(model)
          }
        }).getOrElse(CommonResponse.insertionError(model))
      }).recover(responseRecover[M])
    )
  }

  /** #S-BS-03. */
  def update(model: M): Future[CommonResponse[M]] = {
    logServiceK("update", "S-BS-03", model)(
      dao.update(model).map(resultOpt => {
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(model)
          } else {
            CommonResponse.updateError(model)
          }
        }).getOrElse(CommonResponse.updateError[M])
      }).recover(responseRecover[M])
    )
  }

  /** #S-BS-04. */
  def find(vo: V): Future[CommonResponse[M]] = {
    logServiceK("find", "S-BS-04", vo)(
      dao.find(vo).map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(result)
        ).getOrElse(
          CommonResponse.notFound[M]
        )
      }).recover(responseRecover[M])
    )
  }

  /** #S-BS-05. */
  def findList(vo: V): Future[CommonResponse[Seq[M]]] = {
    logServiceK("findList", "S-BS-05", vo)(
      dao.findList(vo).map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(result)
        ).getOrElse(
          CommonResponse.unknownError(Seq.empty[M])
        )
      }).recover(responseRecover[Seq[M]])
    )
  }

  /** #S-BS-06. */
  def remove(vo: V): Future[CommonResponse[Int]] = {
    logServiceK("remove", "S-BS-06", vo)(
      dao.remove(vo).map(resultOpt => {
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(result)
          } else {
            CommonResponse.notFound[Int]
          }
        }).getOrElse(CommonResponse.notFound[Int])
      }).recover(responseRecover[Int])
    )
  }




  /** #S-BS-01. */
  def all2: Future[CommonResponse[Seq[T]]] = {
    logServiceK("all", "S-BS-01")(
      dao.all().map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(result.map(transformer.from))
        ).getOrElse(
          CommonResponse.unknownError(Seq.empty[T])
        )
      }).recover(responseRecover[Seq[T]])
    )
  }

  /** #S-BS-02. */
  def insert2(dto: T): Future[CommonResponse[T]] = {
    logServiceK("insert2", "S-BS-02-2", dto)({
      val model = transformer.to(dto)
      dao.insert(model).map(resultOpt => {
        val resultDto = transformer.from(model)
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(resultDto)
          } else {
            CommonResponse.insertionError(resultDto)
          }
        }).getOrElse(CommonResponse.insertionError(resultDto))
      }).recover(responseRecover[T])
    })
  }

  /** #S-BS-03. */
  def update2(dto: T): Future[CommonResponse[T]] = {
    logServiceK("update", "S-BS-03", dto)({
      val model = transformer.to(dto)
      dao.update(model).map(resultOpt => {
        val resultDto = transformer.from(model)
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(resultDto)
          } else {
            CommonResponse.updateError(resultDto)
          }
        }).getOrElse(CommonResponse.updateError[T](resultDto))
      }).recover(responseRecover[T])
    })
  }

  /** #S-BS-04. */
  def find2(vo: V): Future[CommonResponse[T]] = {
    logServiceK("find", "S-BS-04", vo)(
      dao.find(vo).map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(transformer.from(result))
        ).getOrElse(
          CommonResponse.notFound[T]
        )
      }).recover(responseRecover[T])
    )
  }

  /** #S-BS-05. */
  def findList2(vo: V): Future[CommonResponse[Seq[T]]] = {
    logServiceK("findList", "S-BS-05", vo)(
      dao.findList(vo).map(resultOpt => {
        resultOpt.map(result =>
          CommonResponse.ok(result.map(transformer.from))
        ).getOrElse(
          CommonResponse.unknownError(Seq.empty[T])
        )
      }).recover(responseRecover[Seq[T]])
    )
  }

  /** #S-BS-06. */
  def remove2(vo: V): Future[CommonResponse[Int]] = {
    logServiceK("remove", "S-BS-06", vo)(
      dao.remove(vo).map(resultOpt => {
        resultOpt.map(result => {
          if (result > 0) {
            CommonResponse.ok(result)
          } else {
            CommonResponse.notFound[Int]
          }
        }).getOrElse(CommonResponse.notFound[Int])
      }).recover(responseRecover[Int])
    )
  }

}
