ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "INDE2_Final_Project"
ThisBuild / organizationName     := "INDE2_Final_Project"

lazy val root = (project in file("."))
  .settings(
    name := "inde2_project",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.8.0",
      "com.typesafe.play" %% "play-json" % "2.9.2",
      "io.github.etspaceman" %% "scalacheck-faker" % "3.0.1",
      "org.apache.spark" %% "spark-core" % "3.2.1",
      "org.apache.spark" %% "spark-sql" % "3.2.1",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.1",
      "org.apache.spark" %% "spark-streaming" % "3.2.1",
      "org.scalameta" %% "munit" % "0.7.29",
      "software.amazon.awssdk" % "s3" % "2.17.52",
      "org.apache.hadoop" % "hadoop-client" % "3.1.2",
      "org.apache.hadoop" % "hadoop-aws" % "3.1.2",
      "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.2",
      "org.apache.hadoop" % "hadoop-common" % "3.1.2",
      "log4j" % "log4j" % "1.2.17",
      "com.typesafe" % "config" % "1.4.1",
      "org.apache.hadoop" % "hadoop-aws" % "3.3.6",
      "software.amazon.awssdk" % "s3" % "2.20.94"
    ),
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
