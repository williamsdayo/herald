package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.{Complaint, CreateComplaint}
import com.rooftopruns.herald.api.counsellor.CounsellorService
import com.rooftopruns.herald.api.message.MessageService
import com.rooftopruns.herald.api.message.Models.CreateMessage
import com.rooftopruns.herald.api.student.StudentService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ComplaintService {

  def find(complaintId: Int) = for {
    complaintRow <- ComplaintRepository.find(complaintId)
    complaint = Complaint(complaintRow.id, complaintRow.title, complaintRow.tag, complaintRow.userId)
  } yield complaint

  def findAllForStudent(token: String): Future[Seq[Complaint]] = for {
    student <- StudentService.findByToken(token)
    complaintRows <- ComplaintRepository.findAllForStudent(student.id)
    complaints = complaintRows.map(row => Complaint(row.id, row.title, row.tag, row.userId))
  } yield complaints

  def findAllForCounsellor(token: String): Future[Seq[Complaint]] = for {
    counsellor <- CounsellorService.findByToken(token)
    complaintRows <- ComplaintRepository.findAllForCounsellor(counsellor.expertise.split(","))
    complaints = complaintRows.map(row => Complaint(row.id, row.title, row.tag, row.userId))
  } yield complaints

  def proclaim(cmd: CreateComplaint, token: String): Future[String] = for {
    student <- StudentService.findByToken(token)
    complaint <- ComplaintRepository.create(cmd, student.id)
    _ <- MessageService.save(CreateMessage(cmd.content, complaint.id, "Question"), token)
  } yield "OK"
}
