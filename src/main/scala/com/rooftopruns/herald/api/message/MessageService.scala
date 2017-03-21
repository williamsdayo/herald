package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.message.Models.CreateMessage
import com.rooftopruns.herald.api.student.StudentService

import scala.concurrent.ExecutionContext.Implicits.global

object MessageService {

  def reply(cmd: CreateMessage, token: String) = {
    for {
      user <- StudentService.findByToken(token)
      _ <- MessageRepository.create(cmd, user.id)
    } yield "OK"
  }
}
