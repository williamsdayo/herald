package com.rooftopruns.herald.api.user

import com.rooftopruns.herald.api.user.Models.{CreateUser, Credentials}
import spray.http.HttpCookie
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

trait UserRoutes { self: HttpService =>

  val users = {
    pathPrefix("users") {
      path("authenticate"){
        post {
          entity(as[Credentials]){ creds =>
            onComplete(UserService.authenticate(creds)) {
              case Success(possibleToken) =>
                possibleToken match {
                  case Some(token) =>
                    setCookie(HttpCookie("token", content = token)) {
                      complete("OK")
                    }
                  case None =>
                    complete("KO")
                }
              case _ => complete("KO")
            }
          }
        }
      } ~
      post {
        entity(as[CreateUser]){ newUser =>
          onComplete(UserService.create(newUser)) {
            _ => complete("OK")
          }
        }
      } ~
      get {
        onComplete(UserService.fetchAll()) {
          case Success(allUsers) => complete(allUsers)
          case _ => complete("KO")
        }
      }
    }
  }
}
