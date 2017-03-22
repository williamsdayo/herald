package com.rooftopruns.herald.api.user

import spray.json.DefaultJsonProtocol

object Models extends DefaultJsonProtocol {

  abstract class User(id: Int, username: String, password: String, email: String)

  case class Credentials(username: String, password: String)
  object Credentials{
    implicit val format = jsonFormat2(Credentials.apply)
  }
}
