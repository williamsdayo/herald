package com.rooftopruns.herald

import akka.actor.ActorSystem
import com.rooftopruns.herald.user.Models.{CreateUser, Credentials, User}
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
    } ~
    path("authenticate"){
      post {
        entity(as[Credentials]){ (creds: Credentials) =>
          complete {

            val rows = Await.result( UserRepository.getall(), 1 second)
            val users: Seq[User] = rows.map(row => User(row.id, row.username, row.password, row.email))

            val matchingUser: Option[User] = users.find(user => user.username == creds.username && user.password == creds.password)

            if (matchingUser != None) {

              val user = matchingUser.get
              val token = java.util.UUID.randomUUID().toString

              Await.result(UserRepository.setToken(user.id, token), 1 second)

              Some(token)
            } else {
              Option.empty[String]
            }
          }
        }
      }
    } ~
    path("login.html") {
      get {
        getFromResource("html/index.html")
      }
    } ~
    path("quit") {
      get {
        complete {
          System.exit(1)
          "Shutting down..."
        }
      }
    } ~
    pathSuffixTest(".*.(css|js|jpg|png)".r) { _ =>
      getFromResourceDirectory("")
    }
  }
}