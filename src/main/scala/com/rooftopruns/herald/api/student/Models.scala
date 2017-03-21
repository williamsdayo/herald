package com.rooftopruns.herald.api.student

import spray.json.DefaultJsonProtocol

/**
  * Created by Dayo on 20/12/2016.
  */
object Models extends DefaultJsonProtocol {

  case class Credentials(username: String, password: String)
  object Credentials{
    implicit val format = jsonFormat2(Credentials.apply)
  }

  case class Student(id: Int, username: String, password: String, email: String)
  object Student{
    implicit val format = jsonFormat4(Student.apply)
  }

  case class CreateStudent(username: String, password: String, email: String)
  object CreateStudent{
    implicit val format = jsonFormat3(CreateStudent.apply)
  }
}

