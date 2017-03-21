package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.CreateComplaint
import com.rooftopruns.herald.api.message.MessageService
import com.rooftopruns.herald.api.message.Models.CreateMessage
import com.rooftopruns.herald.api.user.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ComplaintService {

  def proclaim(cmd: CreateComplaint, token: String): Future[String] = {
    for {
      user <- UserService.findByToken(token)
      complaint <- ComplaintRepository.create(cmd, user.id)
      _ <- MessageService.create(CreateMessage(cmd.content, user.id, complaint.id))
    } yield "OK"
  }
}
