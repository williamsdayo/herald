package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.CreateComplaint
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
  def fetchAll(): Future[Seq[ComplaintsRow]] = {
    db.run(
      Complaints.result
    )
  }

  /**
    * The code below is to create the create the users information in the database
    */
  def create(cmd: CreateComplaint, userId: Int): Future[_root_.slick.Tables.ComplaintsRow] = {
    db.run(
      Complaints returning (Complaints map (_.id)) into ((c, id) => c copy (id = id)) +=
        ComplaintsRow(0, cmd.title, cmd.tag, userId)
    )
  }
}
