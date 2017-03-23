package com.rooftopruns.herald.site

import com.rooftopruns.herald.Boot._
import spray.http.HttpCookie
import spray.routing.HttpService

trait WebPageRoutes { self: HttpService =>

  lazy val pages = {
    path("login.html") {
      get {
        getFromResource("html/login.html")
      }
    } ~
    path("register.html") {
      get {
        getFromResource("html/register.html")
      }
    } ~
    pathPrefix("complaints") {
      path(".html") {
        get {
          getFromResource("html/complaints.html")
        }
      } ~
      pathPrefix(IntNumber) { complaintId =>
        path(".html") {
          setCookie(HttpCookie("complaintId", content = complaintId.toString)) {
            getFromResource("html/messages.html")
          }
        }
      }
    } ~
    path("complain.html") {
      get {
        getFromResource("html/complain.html")
      }
    }
  }

  val resources = {
    pathSuffixTest(".*.(css|js|jpg|png)".r) { _ =>
      getFromResourceDirectory("")
    }
  }

  val quit = {
    path("quit") {
      get {
        complete {
          System.exit(1)
          "Shutting down..."
        }
      }
    }
  }
}
