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
      "software.amazon.awssdk" % "s3" % "2.20.94",
      "org.apache.hadoop" % "hadoop-client" % "3.1.2",
      "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.2",
      "org.apache.hadoop" % "hadoop-common" % "3.1.2",
      "log4j" % "log4j" % "1.2.17",
      "com.typesafe" % "config" % "1.4.1",
      "org.apache.hadoop" % "hadoop-aws" % "3.3.6",
    ),
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.


lazy val dependencies =
    new {
        val playJson = "com.typesafe.play" %% "play-json" % "2.9.2"
        val log4jAPI = "org.apache.logging.log4j" % "log4j-1.2-api" % "2.17.2"
        val log4jCore = "org.apache.logging.log4j" % "log4j-core" % "2.17.2"
        val log4jslf = "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.17.0"
        val kafkaClients = "org.apache.kafka" % "kafka-clients" % "2.8.0"
        val sparkCore = "org.apache.spark" %% "spark-core" % "3.2.1"
        val sparkSQL = "org.apache.spark" %% "spark-sql" % "3.2.1"
        val hadoopCommon = "org.apache.hadoop" % "hadoop-common" % "3.1.2"
        val hadoopMapReduce = "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.2"
        val hadoopAWS = "org.apache.hadoop" % "hadoop-aws" % "3.1.2"
        val hadoopClient = "org.apache.hadoop" % "hadoop-client" % "3.1.2"
        val sparkStreamKafka = "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.1"
        val sparkStream = "org.apache.spark" %% "spark-streaming" % "3.2.1"
        val faker = "io.github.etspaceman" %% "scalacheck-faker" % "3.0.1"
        val awsSDK = "software.amazon.awssdk" % "s3" % "2.17.61"
        val config = "com.typesafe" % "config" % "1.4.1"
    }


lazy val allDependencies = Seq(
  dependencies.playJson,
  dependencies.log4jslf
)

lazy val params = (project in file ("params"))
  .settings(name:= "params",
    libraryDependencies ++= allDependencies ++ Seq()
  )

lazy val generating_data = (project in file("generating_data"))
  .settings(
    name := "generating_data",
    libraryDependencies ++= Seq(
      dependencies.kafkaClients,
      dependencies.log4jAPI,
      dependencies.faker
    ) ++ allDependencies
  ).dependsOn(params)

lazy val s3_storage = (project in file("s3_storage"))
  .settings(
    name := "s3_storage",
    libraryDependencies ++= Seq(
      dependencies.kafkaClients,
      dependencies.hadoopAWS,
      dependencies.awsSDK,
      dependencies.config
    ) ++ allDependencies
  )

lazy val data_analysis = (project in file("data_analysis"))
  .settings(
    name := "data_analysis",
    libraryDependencies ++= Seq(
      dependencies.sparkCore,
      dependencies.sparkSQL,
      dependencies.hadoopCommon,
      dependencies.hadoopMapReduce,
      dependencies.hadoopAWS,
      dependencies.hadoopClient,
      dependencies.config
    ) ++ allDependencies
  ).dependsOn(params)

lazy val alert_handler = (project in file("alert_handler"))
  .settings(
    name := "alert_handler",
    libraryDependencies ++= Seq(
      dependencies.kafkaClients,
      dependencies.sparkCore,
      dependencies.sparkStreamKafka,
      dependencies.sparkStream,
    ) ++ allDependencies,
  ).dependsOn(params)