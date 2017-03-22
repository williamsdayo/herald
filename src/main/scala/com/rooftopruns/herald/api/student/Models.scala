package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.user.Models.User
import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Student(id: Int, username: String, password: String, email: String) extends User(id, username, password, email)
  object Student{
    implicit val format = jsonFormat4(Student.apply)
  }

  case class CreateStudent(username: String, password: String, email: String)
  object CreateStudent{
    implicit val format = jsonFormat3(CreateStudent.apply)
  }
}

