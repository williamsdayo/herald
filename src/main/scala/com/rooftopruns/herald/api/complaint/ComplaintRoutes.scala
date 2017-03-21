package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.CreateComplaint
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global

trait ComplaintRoutes { self: HttpService =>

  val complaints = {
    cookie("token") { tokenCookie =>
      path("proclaim"){
        post {
          entity(as[CreateComplaint]) { complaint =>
            onComplete(ComplaintService.proclaim(complaint, tokenCookie.content)) {
              _ => complete("OK")
            }
          }
        }
      }
    }
  }
}
