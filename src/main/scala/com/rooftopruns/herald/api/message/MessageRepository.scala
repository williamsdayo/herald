package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.message.Models.CreateMessage
import slick.Tables._
import slick.jdbc.JdbcBackend

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MessageRepository {

  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  def create(cmd: CreateMessage, userId: Int) = {
    db.run(
      Messages returning (Messages map (_.id)) into ((m, id) => m copy (id = id)) +=
        MessagesRow(0, cmd.content, userId, cmd.complaintId, cmd.kind)
    )
  }

  def fetchAll: Future[Seq[MessagesRow]] = {
    db.run(
      Messages.result
    )
  }

  def fetchByComplaint(complaintId: Int) = {
    fetchAll
      .map { rows =>
        rows.filter(_.complaintId == complaintId)
      }
  }
}
