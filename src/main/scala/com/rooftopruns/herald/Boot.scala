package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.user.Models.User
import spray.routing.SimpleRoutingApp
import spray.httpx.SprayJsonSupport._

object Boot extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  /*code below gotten from spray */

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      } ~
      post {
        entity(as[User]){ (bob: User) =>
          complete {
            println(bob.password)

            <h1>User message has been recieved</h1>
          }
        }
      }
    }
  }
}