package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.student.Models.{CreateStudent, Credentials, Student}
import slick.Tables.StudentsRow

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object StudentService {

  def create(newUser: CreateStudent) = for {
    _ <- StudentRepository.create(newUser)
    token <- authenticate(Credentials(newUser.username, newUser.password))
  } yield token

  def findByToken(token: String): Future[StudentsRow] = StudentRepository.findByToken(token).map(_.get)

  def fetchAll(): Future[Seq[Student]] = for {
    userRows <- StudentRepository.fetchAll()
    users = userRows.map(row => Student(row.id, row.username, row.password, row.email))
  } yield users

  def authenticate(creds: Credentials): Future[Option[String]] = for {
    userRows <- StudentRepository.fetchAll()
    matchingUser = userRows.find(user => user.username == creds.username && user.password == creds.password)
    possibleToken = {
      val token = java.util.UUID.randomUUID().toString
      matchingUser
        .map { user => StudentRepository.setToken(user.id, token) }
        .map { _ => token }
    }
  } yield possibleToken
}
