import Dependencies._

ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "inde2_project",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.8.0",
      "com.typesafe.play" %% "play-json" % "2.9.2"
    ),
    libraryDependencies += munit % Test
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
