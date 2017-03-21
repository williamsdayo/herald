package com.rooftopruns.herald.api.user

import com.rooftopruns.herald.api.user.Models.{CreateUser, Credentials, User}
import slick.Tables.UsersRow
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object UserService {

  def create(newUser: CreateUser) = UserRepository.create(newUser)

  def findByToken(token: String): Future[UsersRow] = UserRepository.findByToken(token).map(_.get)

  def fetchAll(): Future[Seq[User]] = for {
    userRows <- UserRepository.getall()
    users = userRows.map(row => User(row.id, row.username, row.password, row.email))
  } yield users

  def authenticate(creds: Credentials): Future[Option[String]] = for {
    userRows <- UserRepository.getall()
    matchingUser = userRows.find(user => user.username == creds.username && user.password == creds.password)
    possibleToken = {
      val token = java.util.UUID.randomUUID().toString
      matchingUser
        .map { user => UserRepository.setToken(user.id, token) }
        .map { _ => token }
    }
  } yield possibleToken
}
