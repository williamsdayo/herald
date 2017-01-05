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
    sourceGenerators in Compile <+= slick,
    managedSourceDirectories in Compile += sourceManaged.value / "codegen",
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
    ),
    slick <<= (runner in Compile, dependencyClasspath in Compile, sourceManaged, streams) map { (r, cp, src, s) =>
      val outputDir = (src / "codegen" ).getPath
      val (pkg, jdbcDriver, slickDriver) = ("slick", "com.mysql.jdbc.Driver", "slick.driver.MySQLDriver")
      val (url, username, password) = ("jdbc:mysql://localhost/app", "app", "password")
      val options = Seq(slickDriver, jdbcDriver, url, outputDir, pkg, username, password)
      toError(
        r.run("slick.codegen.SourceCodeGenerator", cp.files, options, s.log))
      Seq(file(s"$outputDir/$pkg/Tables.scala"))
    }
  )

lazy val slick = TaskKey[Seq[File]]("slick", "generate slick table classes")
