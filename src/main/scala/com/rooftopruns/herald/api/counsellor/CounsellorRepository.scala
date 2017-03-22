package com.rooftopruns.herald.api.counsellor

import slick.Tables._
import slick.jdbc.JdbcBackend

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Dayo on 20/12/2016.
  */
object CounsellorRepository {
  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  /**
    *The info below tells the database to give all the users information
    * db.run basically means database run
    */
  def fetchAll(): Future[Seq[CounsellorsRow]] = {
    db.run(
      Counsellors.result
    )
  }

  def findByToken(token: String): Future[Option[CounsellorsRow]] = {
    fetchAll()
      .map { rows =>
        rows.find(_.token.exists(_ == token))
      }
  }

  /**
    * The code below is used to change a user's token in the database
    */
  def setToken(userId: Int, token: String) = {
    db.run(
      Counsellors.filter(_.id === userId).map(_.token).update(Some(token))
    )
  }
}
