package com.rooftopruns.herald.user

import com.rooftopruns.herald.user.Models.CreateUser
import slick.jdbc.JdbcBackend
import slick.Tables._

import scala.concurrent.Future

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
  def getall(): Future[Seq[UsersRow]] = {
    db.run(
      Users.result
    )
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
