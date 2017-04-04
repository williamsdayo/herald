package com.rooftopruns.herald.api.student

import com.rooftopruns.herald.api.student.Models.CreateStudent
import com.rooftopruns.herald.api.user.Models.Credentials
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

trait StudentRoutes { self: HttpService =>

  val students = {
    pathPrefix("students") {
      path("authenticate"){
        post {
          entity(as[Credentials]){ creds =>
            onComplete(StudentService.authenticate(creds)) {
              case Success(possibleToken) =>
                possibleToken match {
                  case Some(token) => complete(token)
                  case None => complete("KO")
                }
              case Failure(ex) => complete(s"KO: ${ex.getMessage}")
            }
          }
        }
      } ~
      path("register"){
        post {
          entity(as[CreateStudent]){ student =>
            onComplete(StudentService.create(student)) {
              case Success(possibleToken) =>
                possibleToken match {
                  case Some(token) => complete(token)
                  case None => complete("KO")
                }
              case Failure(ex) => complete(s"KO: ${ex.getMessage}")
            }
          }
        }
      } ~
      get {
        onComplete(StudentService.fetchAll()) {
          case Success(allUsers) => complete(allUsers)
          case Failure(ex) => complete(s"KO: ${ex.getMessage}")
        }
      }
    }
  }
}
