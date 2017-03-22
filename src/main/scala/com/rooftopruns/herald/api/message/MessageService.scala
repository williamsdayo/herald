package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.counsellor.CounsellorService
import com.rooftopruns.herald.api.message.Models.{CreateMessage, Message, Question, Reply}
import com.rooftopruns.herald.api.student.Models.Student
import com.rooftopruns.herald.api.student.StudentService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MessageService {

  def findByComplaint(complaintId: Int, token: String): Future[Seq[Message]] = for {
    studentRow <- StudentService.findByToken(token)
    student = Student(studentRow.id, studentRow.username, studentRow.password, studentRow.email)
    messageRows <- MessageRepository.fetchByComplaint(complaintId)
    counsellors <- CounsellorService.fetchAll()
    messages = messageRows.map {
      case row if row.kind == "Question" => Question(row.content, student)
      case row if row.kind == "Reply" => Reply(row.content, counsellors.find(_.id == row.userId).get)
    }
  } yield messages

  def question(cmd: CreateMessage, token: String) = for {
    student <- StudentService.findByToken(token)
    _ <- MessageRepository.create(cmd, student.id)
  } yield "OK"

  def reply(cmd: CreateMessage, token: String) = for {
    student <- CounsellorService.findByToken(token)
    _ <- MessageRepository.create(cmd, student.id)
  } yield "OK"
}
