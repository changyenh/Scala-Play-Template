package com.crystalapps.example.backend.database.dao

import com.crystalapps.example.backend.database.models.ServiceSettingModels.{ServiceSettingModel, ServiceSettingVo}
import javax.inject.Inject
import com.crystalapps.example.backend.database.models.BasicModels.BasicColumns
import com.crystalapps.example.backend.database.models.ServiceSettingModels._
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext

/**
  * Created by changyenh on 西暦18/05/04.
  */
class ServiceSettingDao @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider)
                                 (implicit executionContext: ExecutionContext) extends BasicDao[ServiceSettingModel, ServiceSettingVo](dbConfigProvider) {

  import com.crystalapps.example.backend.common.DaoHelper._
  import profile.api._

  private val tableQuery: TableQuery[ModelTable] = TableQuery[ModelTable]
  override protected val superTableQuery: TableQuery[_ <: ModelTableAbstract] = tableQuery

  /** #D-SSD-01. */
  override protected def filter(vo: ServiceSettingVo): profile.api.Query[_ <: ModelTableAbstract, ServiceSettingModel, Seq] = {
    logDaoK("filter", "D-SSD-01", vo)(
      tableQuery.optionalFilter(vo.serviceName)(_.serviceName === _)
          .optionalFilter(vo.serviceValue)(_.serviceValue === _)
    )
  }

  /** #D-SSD-02. */
  override protected def filterPk(model: ServiceSettingModel): profile.api.Query[_ <: ModelTableAbstract, ServiceSettingModel, Seq] = {
    logDaoK("filterPk", "D-SSD-02", model)(
      tableQuery.filter(_.serviceName === model.serviceName)
    )
  }

  class ModelTable(tag: Tag) extends ModelTableAbstract(tag, "CMServiceSetting") {
    def serviceName = column[String]("cmServiceName", O.PrimaryKey)

    def serviceValue = column[String]("cmServiceValue")

    def * = (serviceName, serviceValue, BasicColumns(createdAt, updatedAt, active)) <> ((ServiceSettingModel.apply _).tupled, ServiceSettingModel.unapply)
  }

}
