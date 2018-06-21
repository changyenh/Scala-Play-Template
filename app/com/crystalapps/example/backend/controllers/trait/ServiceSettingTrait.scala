package com.crystalapps.example.backend.controllers.`trait`

import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel
import com.crystalapps.example.backend.database.models.ServiceSettingModels.ServiceSettingModel

/**
  * Created by changyenh on 西暦18/05/08.
  */
trait ServiceSettingTrait extends BasicFunctions[ServiceSettingModel] {

  /**
    * The default body parser that’s used if you do not explicitly select a body parser will look at the incoming Content-Type header, and parses the body accordingly. So for example, a Content-Type of type application/json will be parsed as a JsValue, while a Content-Type of application/t-www-form-urlencoded will be parsed as a Map[String, Seq[String]].
    */

}
