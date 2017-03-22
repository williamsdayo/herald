package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.api.complaint.ComplaintRoutes
import com.rooftopruns.herald.api.counsellor.CounsellorRoutes
import com.rooftopruns.herald.api.student.StudentRoutes
import com.rooftopruns.herald.site.WebPageRoutes
import spray.routing.SimpleRoutingApp

object Boot extends App
  with SimpleRoutingApp
  with WebPageRoutes
  with StudentRoutes
  with CounsellorRoutes
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
