package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.api.complaint.ComplaintRoutes
import com.rooftopruns.herald.api.user.UserRoutes
import com.rooftopruns.herald.site.WebPageRoutes
import spray.routing.SimpleRoutingApp

object Boot extends App
  with SimpleRoutingApp
  with WebPageRoutes
  with UserRoutes
  with ComplaintRoutes {

  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    pages ~
    resources ~
    users ~
    complaints ~
    quit
  }
}
