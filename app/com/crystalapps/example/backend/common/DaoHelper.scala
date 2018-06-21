package com.crystalapps.example.backend.common

import scala.language.higherKinds

/**
  * Created by changyenh on 西暦18/05/06.
  */
object DaoHelper {

  import com.crystalapps.example.backend.database.DefaultProfile.api._

  implicit class OptionFilter[E, U, C[_]](query: Query[E, U, C]) extends CommonClass {

    import slick.lifted.CanBeQueryCondition

    /** #F-DH_OF-01. */
    def filterIf[O, T <: Rep[_]](condition: Boolean)
                                (f: E => Rep[Boolean]): Query[E, U, C] = {
      logFuncK("filterId", "F-DH_OF-01", condition)(
        if (condition) query.filter(f) else query
      )
    }

    /** #F-DH_OF-02. */
    def optionalFilter[O, T <: Rep[_]](op: Option[O])
                                      (f: (E, O) => T)
                                      (implicit wt: CanBeQueryCondition[T]): Query[E, U, C] = {
      logFuncK("optionalFilter", "F-DH_OF-02", op)(
        op.map(o => query.filter(f(_, o))).getOrElse(query)
      )
    }

    /** #F-DH_OF-03. */
    /*def optionalEqualFilter[O, T <: Rep[_]](op: Option[O])
                                           (f: (E, O) => T)
                                           (implicit wt: CanBeQueryCondition[T]): Query[E, U, C] = {
      logFuncK("optionalEqualFilter", "F-DH_OF-03", op)(
        op.map(o => query.filter(f(_, o))).getOrElse(query)
      )
    }*/
  }

}