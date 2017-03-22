package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.CreateComplaint
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

trait ComplaintRoutes { self: HttpService =>

  val complaints = {
    cookie("token") { tokenCookie =>
      path("complaints"){
        post {
          entity(as[CreateComplaint]) { complaint =>
            onComplete(ComplaintService.proclaim(complaint, tokenCookie.content)) {
              _ => complete("OK")
            }
          }
        } ~
        get {
          onComplete(ComplaintService.findByUser(tokenCookie.content)) {
            case Success(userComplaints) => complete(userComplaints)
            case _ => complete("KO")
          }
        }
      }
    }
  }
}
