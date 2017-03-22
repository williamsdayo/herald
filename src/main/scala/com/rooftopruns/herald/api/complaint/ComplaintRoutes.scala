package com.rooftopruns.herald.api.complaint

import com.rooftopruns.herald.api.complaint.Models.CreateComplaint
import com.rooftopruns.herald.api.message.MessageService
import com.rooftopruns.herald.api.message.Models.CreateMessage
import spray.http.HttpCookie
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

trait ComplaintRoutes { self: HttpService =>

  val complaints = {
    cookie("token") { tokenCookie =>
      pathPrefix("complaints"){
        post {
          entity(as[CreateComplaint]) { complaint =>
            onComplete(ComplaintService.proclaim(complaint, tokenCookie.content)) {
              _ => complete("OK")
            }
          }
        } ~
        pathPrefix(IntNumber) { complaintId =>
          path("messages"){
            get {
              onComplete(MessageService.findByComplaint(complaintId, tokenCookie.content)) {
                case Success(complaintMessages) => complete(complaintMessages)
                case _ => complete("KO")
              }
            }
          } ~
          setCookie(HttpCookie("complaintId", content = complaintId.toString)) {
            getFromResource("html/messages.html")
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
