package com.rooftopruns.herald.api.complaint

import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Complaint(id: Int, title: String, content: String, tag: String)
  object Complaint{
    implicit val format = jsonFormat4(Complaint.apply)
  }

  case class CreateComplaint(title: String, content: String, tag: String)
  object CreateComplaint{
    implicit val format = jsonFormat3(CreateComplaint.apply)
  }
}

