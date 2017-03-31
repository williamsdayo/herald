package com.rooftopruns.herald.api.counsellor

import com.rooftopruns.herald.api.user.Models.User
import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Counsellor(id: Int, username: String, password: String, email: String, tags: Seq[String]) extends User
  object Counsellor{
    implicit val format = jsonFormat5(Counsellor.apply)
  }
}

