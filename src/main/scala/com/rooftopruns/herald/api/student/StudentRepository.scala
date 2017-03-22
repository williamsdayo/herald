package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.student.Models.CreateStudent
import slick.Tables._
import slick.jdbc.JdbcBackend

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Dayo on 20/12/2016.
  */
object StudentRepository {
  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  /**
    *The info below tells the database to give all the users information
    * db.run basically means database run
    */
  def fecthAll(): Future[Seq[StudentsRow]] = {
    db.run(
      Students.result
    )
  }

  def findByToken(token: String): Future[Option[StudentsRow]] = {
    fecthAll()
      .map { rows =>
        rows.find(_.token.exists(_ == token))
      }
  }

  /**
    * The code below is to create the create the users information in the database
    */
  def create(cmd: CreateStudent): Future[Int] = {
    db.run(
      Students += StudentsRow(0, cmd.username, cmd.password, cmd.email)
    )
  }

  

  /**
    * The code below is used to change a user's token in the database
    */
  def setToken(userId: Int, token: String) = {
    db.run(
      Students.filter(_.id === userId).map(_.token).update(Some(token))
    )
  }
}
