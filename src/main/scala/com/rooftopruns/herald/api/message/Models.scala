package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.counsellor.Models.Counsellor
import com.rooftopruns.herald.api.student.Models.Student
import com.rooftopruns.herald.api.user.Models.User
import spray.json.{DefaultJsonProtocol, JsObject, JsString, JsValue, RootJsonFormat, _}

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  trait Message {
    def content: String
    def user: User
    def kind: String
  }

  implicit object MessageFormat extends RootJsonFormat[Message] {

    override def write(obj: Message): JsValue = {

      val userJson = if (obj.kind == "Question") {
        obj.user.asInstanceOf[Student].toJson
      } else {
        obj.user.asInstanceOf[Counsellor].toJson
      }
      JsObject(
        "content" -> obj.content.toJson,
        "user" -> userJson,
        "kind" -> obj.kind.toJson
      )
    }

    override def read(json: JsValue): Message = {
      json.asJsObject.getFields("content", "user", "kind") match {
        case Seq(JsString(content), userObj, JsString(kind)) =>
          if (kind == "Question") {
            val user = userObj.convertTo[Student]
            Question(content, user)
          } else {
            val user = userObj.convertTo[Counsellor]
            Reply(content, user)
          }
      }
    }
  }


  case class Question(content: String, user: Student) extends Message {
    override val kind = "Question"
  }
  object Question{
    implicit val format = jsonFormat2(Question.apply)
  }

  case class Reply(content: String, user: Counsellor) extends Message {
    override val kind = "Question"
  }
  object Reply{
    implicit val format = jsonFormat2(Reply.apply)
  }

  case class CreateMessage(content: String, complaintId: Int, kind: String)
  object CreateMessage{
    implicit val format = jsonFormat3(CreateMessage.apply)
  }
}

