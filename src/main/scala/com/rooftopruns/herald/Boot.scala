package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.user.Models.{CreateUser, User}
import com.rooftopruns.herald.user.UserRepository
import spray.routing.SimpleRoutingApp
import spray.httpx.SprayJsonSupport._
import scala.concurrent.Await
import scala.concurrent.duration._
import spray.json.DefaultJsonProtocol._

object Boot extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  /*code below gotten from spray */

  startServer(interface = "localhost", port = 8080) {
    path("users") {
      get {
        complete {
          val rows = Await.result( UserRepository.getall(), 1 second)
          val users = rows.map(row => User(row.id, row.username, row.password, row.email))
          users
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