package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.Complaint
import com.rooftopruns.herald.api.user.UserService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ComplaintService {

  def proclaim(complaint: Complaint, token: String): Future[String] = {
    for {
      user <- UserService.findByToken(token)
      _ <- ComplaintRepository.create(complaint, user.id)
    } yield "OK"
  }
}
