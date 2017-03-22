package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.student.Models.{CreateStudent, Credentials}
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

trait StudentRoutes { self: HttpService =>

  val users = {
    pathPrefix("users") {
      path("authenticate"){
        post {
          entity(as[Credentials]){ creds =>
            onComplete(StudentService.authenticate(creds)) {
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
      path("students") {
        post {
          entity(as[CreateStudent]){ newUser =>
            onComplete(StudentService.create(newUser)) {
              _ => complete("OK")
            }
          }
        } ~
        get {
          onComplete(StudentService.fetchAll()) {
            case Success(allUsers) => complete(allUsers)
            case _ => complete("KO")
          }
        }
      }
    }
  }
}
