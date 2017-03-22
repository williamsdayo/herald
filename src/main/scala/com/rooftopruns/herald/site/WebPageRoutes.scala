package com.rooftopruns.herald.site

import com.rooftopruns.herald.Boot._
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
    path("complaint.html") {
      get {
        getFromResource("html/complaint.html")
      }
    } ~
    path("dashboard.html") {
      get {
        getFromResource("html/dashboard.html")
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
