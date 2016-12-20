package com.rooftopruns.herald.user

import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class User(id: Int, username: String, password: String, email: String)
  object User{
    implicit val format = jsonFormat4(User.apply)
  }
}
