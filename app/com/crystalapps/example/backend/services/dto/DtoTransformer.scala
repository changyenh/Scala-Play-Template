package com.crystalapps.example.backend.services.dto

/**
  * Created by changyenh on 西暦18/05/12.
  */
trait DtoTransformer[DTO, MODEL] {

  def from(model: MODEL): DTO

  def to(dto: DTO): MODEL

}
