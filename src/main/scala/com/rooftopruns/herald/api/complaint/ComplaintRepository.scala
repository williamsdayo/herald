package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.Complaint
import slick.Tables._
import slick.jdbc.JdbcBackend

import scala.concurrent.Future

/**
  * Created by Dayo on 20/12/2016.
  */
object ComplaintRepository {
  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  /**
    *The info below tells the database to give all the users information
    * db.run basically means database run
    */
  def getall(): Future[Seq[ComplaintsRow]] = {
    db.run(
      Complaints.result
    )
  }

  /**
    * The code below is to create the create the users information in the database
    */
  def create(complaint: Complaint, userId: Int): Future[Int] = {
    db.run(
      Complaints += ComplaintsRow(0, complaint.title, complaint.tag, userId)
    )
  }
}
