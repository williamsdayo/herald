package com.rooftopruns.herald

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object Boot extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  /*code below gotten from spray */

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
  }
}