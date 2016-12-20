import sbt.Keys._

val sprayVersion = "1.3.2"
val slickVersion = "3.1.1"
val akkaVersion = "2.3.9"

lazy val root = (project in file("."))
  .settings(
    name := "herald",
    version := "1.0",
    scalaVersion := "2.11.8",
    resolvers ++= Seq(
      "Spray Repository" at "http://repo.spray.io",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    fork in run := true,
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % sprayVersion,
      "io.spray" %% "spray-json" % sprayVersion,
      "io.spray" %% "spray-routing" % sprayVersion,
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-codegen" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "mysql" % "mysql-connector-java" % "5.1.38"
    )
  )
