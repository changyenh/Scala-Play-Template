package com.crystalapps.example.backend.database.dao.`trait`

import slick.lifted.Query

import scala.concurrent.Future

/**
  * Created by changyenh on 西暦18/05/14.
  */
trait BasicDaoOperations[M, V, T] {

  protected def filter(vo: V): Query[T, M, Seq]

  /** #D-BD-02. */
  protected def filterPk(model: M): Query[T, M, Seq]

  /** #D-BD-03. */
  def all(): Future[Option[Seq[M]]]

  /** #D-BD-04. */
  def find(vo: V): Future[Option[M]]

  /** #D-BD-05. */
  def findList(vo: V): Future[Option[Seq[M]]]

  /** #D-BD-06. */
  def update(model: M): Future[Option[Int]]

  /** #D-BD-07. */
  def insertOrUpdate(model: M): Future[Option[Int]]

  /** #D-BD-08. */
  def insert(model: M): Future[Option[M]]

  /** #D-BD-09. */
  def remove(vo: V): Future[Option[Int]]

}
