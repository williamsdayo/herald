package com.rooftopruns.herald.api.message

import com.rooftopruns.herald.api.message.Models.CreateMessage
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global

trait MessageRoutes { self: HttpService =>

  val messages = {
    cookie("token") { tokenCookie =>
      path("messages"){
        post {
          entity(as[CreateMessage]) { message =>
            onComplete(MessageService.reply(message, tokenCookie.content)) {
              _ => complete("OK")
            }
          }
        }
      }
    }
  }
}
