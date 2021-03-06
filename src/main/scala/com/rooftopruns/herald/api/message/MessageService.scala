package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.complaint.ComplaintService
import com.rooftopruns.herald.api.counsellor.Models.Counsellor
import com.rooftopruns.herald.api.counsellor.{CounsellorRepository, CounsellorService}
import com.rooftopruns.herald.api.message.Models.{CreateMessage, Message, Question, Reply}
import com.rooftopruns.herald.api.student.Models.Student
import com.rooftopruns.herald.api.student.{StudentRepository, StudentService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MessageService {

  def findByComplaint(complaintId: Int, token: String): Future[Seq[Message]] = for {
    complaint <- ComplaintService.find(complaintId)
    students <- StudentService.fetchAll()
    student = students.find(_.id == complaint.userId).get
    messageRows <- MessageRepository.fetchByComplaint(complaintId)
    counsellors <- CounsellorService.fetchAll()
    messages = messageRows.map {
      case row if row.kind == "Question" => Question(row.content, student)
      case row if row.kind == "Reply" => Reply(row.content, counsellors.find(_.id == row.userId).get)
    }
  } yield messages

  def save(cmd: CreateMessage, token: String) = for {
    students <- StudentRepository.fetchAll()
    possibleStudent = students.find(_.token.contains(token)).map(s => Student(s.id, s.username, s.password, s.email))
    counsellors <- CounsellorRepository.fetchAll()
    possibleCounsellor = counsellors.find(_.token.contains(token)).map(s => Counsellor(s.id, s.username, s.password, s.email, s.expertise.split(",")))
    user = possibleStudent.orElse(possibleCounsellor)
    kind = if (students.flatMap(_.token).contains(token)) "Question" else "Reply"
    _ <- MessageRepository.create(cmd.copy(kind = kind), user.get.id)
  } yield "OK"
}
