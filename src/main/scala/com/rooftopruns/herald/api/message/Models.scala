package com.rooftopruns.herald.api.message

import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Message(content: String, userId: Int)
  object Message{
    implicit val format = jsonFormat2(Message.apply)
  }

  case class CreateMessage(content: String, complaintId: Int)
  object CreateMessage{
    implicit val format = jsonFormat2(CreateMessage.apply)
  }
}

