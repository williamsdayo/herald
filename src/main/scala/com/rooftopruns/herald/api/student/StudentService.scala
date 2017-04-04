package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.student.Models.{CreateStudent, Student}
import com.rooftopruns.herald.api.user.Models.Credentials

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object StudentService {

  def create(newUser: CreateStudent) = for {
    _ <- StudentRepository.create(newUser)
    token <- authenticate(Credentials(newUser.username, newUser.password))
  } yield token

  def findByToken(token: String): Future[Student] = for {
    studentRow <- StudentRepository.findByToken(token)
    student = Student(studentRow.id, studentRow.username, studentRow.password, studentRow.email)
  } yield student

  def fetchAll(): Future[Seq[Student]] = for {
    userRows <- StudentRepository.fetchAll()
    users = userRows.map(row => Student(row.id, row.username, row.password, row.email))
  } yield users

  def authenticate(creds: Credentials): Future[Option[String]] = for {
    studentRows <- StudentRepository.fetchAll()
    matchingUser = studentRows.find(student => student.username == creds.username && student.password == creds.password)
    possibleToken = {
      val token = java.util.UUID.randomUUID().toString
      matchingUser
        .map { student => StudentRepository.setToken(student.id, token) }
        .map { _ => token }
    }
  } yield possibleToken
}
