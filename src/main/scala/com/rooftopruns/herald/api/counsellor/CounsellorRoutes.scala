package com.rooftopruns.herald.api.counsellor

import com.rooftopruns.herald.api.counsellor.Models._
import com.rooftopruns.herald.api.message.MessageService
import com.rooftopruns.herald.api.message.Models.CreateMessage
import com.rooftopruns.herald.api.user.Models.Credentials
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

trait CounsellorRoutes { self: HttpService =>

  val counsellor = {
    pathPrefix("counsellors") {
      path("authenticate"){
        post {
          entity(as[Credentials]){ creds =>
            onComplete(CounsellorService.authenticate(creds)) {
              case Success(possibleToken) =>
                possibleToken match {
                  case Some(token) => complete(token)
                  case None => complete("KO")
                }
              case _ => complete("KO")
            }
          }
        }
      } ~
      cookie("token") { tokenCookie =>
        path("messages") {
          post {
            entity(as[CreateMessage]) { message =>
              onComplete(MessageService.reply(message, tokenCookie.content)) {
                _ => complete("OK")
              }
            }
          }
        }
      } ~
      get {
        onComplete(CounsellorService.fetchAll()) {
          case Success(allUsers) => complete(allUsers)
          case _ => complete("KO")
        }
      }
    }
  }
}
