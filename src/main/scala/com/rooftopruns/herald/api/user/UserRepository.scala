package com.rooftopruns.herald.api.user

import com.rooftopruns.herald.api.user.Models.CreateUser
import slick.Tables._
import slick.jdbc.JdbcBackend

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Dayo on 20/12/2016.
  */
object UserRepository {
  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  /**
    *The info below tells the database to give all the users information
    * db.run basically means database run
    */
  def fecthAll(): Future[Seq[UsersRow]] = {
    db.run(
      Users.result
    )
  }

  def findByToken(token: String): Future[Option[UsersRow]] = {
    fecthAll()
      .map { rows =>
        rows.find(_.token.exists(_ == token))
      }
  }

  /**
    * The code below is to create the create the users information in the database
    */
  def create(cmd: CreateUser): Future[Int] = {
    db.run(
      Users += UsersRow(0, cmd.username, cmd.password, cmd.email)
    )
  }

  

  /**
    * The code below is used to change a user's token in the database
    */
  def setToken(userId: Int, token: String) = {
    db.run(
      Users.filter(_.id === userId).map(_.token).update(Some(token))
    )
  }
}
