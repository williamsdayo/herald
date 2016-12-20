import sbt.Keys._

lazy val root = (project in file ("."))
    .settings (
        name := "herald",
        version := "1.0",
        scalaVersion := "2.12.1"
      )
