package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.user.Models.{CreateUser, User}
import com.rooftopruns.herald.user.UserRepository
import spray.routing.SimpleRoutingApp
import spray.httpx.SprayJsonSupport._

object Boot extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  /*code below gotten from spray */

  startServer(interface = "localhost", port = 8080) {
    path("users") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      } ~
      post {
        entity(as[CreateUser]){ (bob: CreateUser) =>
          complete {
            println(bob.password)
            UserRepository.create(CreateUser(bob.username, bob.password, bob.email))
            <h1>User message has been recieved</h1>
          }
        }
      }
    }
  }
}