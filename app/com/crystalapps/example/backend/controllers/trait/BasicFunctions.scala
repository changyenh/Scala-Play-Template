package com.crystalapps.example.backend.controllers.`trait`

import play.api.mvc.{Action, AnyContent}

/**
  * Created by changyenh on 西暦18/05/08.
  */
trait BasicFunctions[T] extends ControllerTrait {

  /** #A-BF-01 all. */
  def all: Action[AnyContent]

  /** #A-BF-02 insert. */
  def insert: Action[T]

  /** #A-BF-03 update. */
  def update: Action[T]

  /** #A-BF-04 find. */
  def find(id: String): Action[AnyContent]

  /** #A-BF-05 remove. */
  def remove(id: String): Action[AnyContent]

}
