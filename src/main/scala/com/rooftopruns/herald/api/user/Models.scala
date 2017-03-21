package com.rooftopruns.herald.api.user

import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Credentials(username: String, password: String)
  object Credentials{
    implicit val format = jsonFormat2(Credentials.apply)
  }

  case class User(id: Int, username: String, password: String, email: String)
  object User{
    implicit val format = jsonFormat4(User.apply)
  }

  case class CreateUser(username: String, password: String, email: String)
  object CreateUser{
    implicit val format = jsonFormat3(CreateUser.apply)
  }
}

