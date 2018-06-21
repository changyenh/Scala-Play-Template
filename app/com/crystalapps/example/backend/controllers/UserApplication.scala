package com.crystalapps.example.backend.controllers

import com.crystalapps.example.backend.controllers.`trait`.ControllerTrait
import com.crystalapps.example.backend.services.UserService
import javax.inject.Inject

import scala.concurrent.ExecutionContext

class UserApplication @Inject()(service: UserService)(implicit executionContext: ExecutionContext)
    extends ControllerTrait {

}



