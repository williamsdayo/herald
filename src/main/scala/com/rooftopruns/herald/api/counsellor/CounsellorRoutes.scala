package com.rooftopruns.herald.api.counsellor

import com.rooftopruns.herald.api.counsellor.Models._
import com.rooftopruns.herald.api.user.Models.Credentials
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

trait CounsellorRoutes { self: HttpService =>

  val counsellors = {
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
              case Failure(ex) => complete(s"KO: ${ex.getMessage}")
            }
          }
        }
      } ~
      get {
        onComplete(CounsellorService.fetchAll()) {
          case Success(allUsers) => complete(allUsers)
          case Failure(ex) => complete(s"KO: ${ex.getMessage}")
        }
      }
    }
  }
}
