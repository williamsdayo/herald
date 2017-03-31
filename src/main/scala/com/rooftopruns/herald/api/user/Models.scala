package com.rooftopruns.herald.api.user

import spray.json.DefaultJsonProtocol

object Models extends DefaultJsonProtocol {

  trait User {
    def id: Int
    def username: String
    def password: String
    def email: String
  }

  case class Credentials(username: String, password: String)
  object Credentials{
    implicit val format = jsonFormat2(Credentials.apply)
  }
}
