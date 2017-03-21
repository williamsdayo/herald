package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.Complaint
import spray.routing.HttpService
import spray.httpx.SprayJsonSupport._

import scala.concurrent.ExecutionContext.Implicits.global

trait ComplaintRoutes { self: HttpService =>

  val complaints = {
    cookie("token") { tokenCookie =>
      path("proclaim"){
        post {
          entity(as[Complaint]) { complaint =>
            onComplete(ComplaintService.proclaim(complaint, tokenCookie.content)) {
              _ => complete("OK")
            }
          }
        }
      }
    }
  }
}
