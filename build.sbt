ThisBuild / scalaVersion     := "2.13.11"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "INDE2_Final_Project"
ThisBuild / organizationName     := "INDE2_Final_Project"



lazy val params = (project in file ("params"))
.settings(name:= "params",
      libraryDependencies ++= allDependencies ++ Seq()
      )

lazy val alertHandler = (project in file ("alert_handler"))
.settings(
          name := "alert_handler",
          libraryDependencies ++=allDependencies ++ Seq(
            dependencies.kafkaClients,
            dependencies.sparkCore,
            dependencies.sparkSQL,
            dependencies.sparkStreamKafka,
            dependencies.sparkStream
          ),
          mainClass := Some("inde2_project.alert_handler.Main")
)


lazy val allDependencies = Seq(dependencies.playJson,dependencies.log4jslf)

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
    }


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
