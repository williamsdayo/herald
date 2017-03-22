package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.{Complaint, CreateComplaint}
import com.rooftopruns.herald.api.message.MessageService
import com.rooftopruns.herald.api.message.Models.CreateMessage
import com.rooftopruns.herald.api.student.StudentService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ComplaintService {

  def findByUser(token: String): Future[Seq[Complaint]] = for {
    student <- StudentService.findByToken(token)
    complaintRows <- ComplaintRepository.findByUserId(student.id)
    complaints = complaintRows.map(row => Complaint(row.id, row.title, row.tag))
  } yield complaints

  def proclaim(cmd: CreateComplaint, token: String): Future[String] = for {
    student <- StudentService.findByToken(token)
    complaint <- ComplaintRepository.create(cmd, student.id)
    _ <- MessageService.reply(CreateMessage(cmd.content, complaint.id, "Question"), token)
  } yield "OK"
}
