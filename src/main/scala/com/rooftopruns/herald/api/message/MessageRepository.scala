package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.message.Models.CreateMessage
import slick.jdbc.JdbcBackend
import slick.Tables._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object MessageRepository {

  import slick.driver.MySQLDriver.api._

  val db = JdbcBackend.Database.forConfig("herald")

  def create(cmd: CreateMessage) = {
    db.run(
      Messages += MessagesRow(0, cmd.content, cmd.userId, cmd.complaintId)
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
