package dataAnalysis
import params._
import play.api.libs.json._
import java.io.FileNotFoundException
import java.time.format.DateTimeFormatter 
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkContext, SparkConf}


object Main {
    def main(args: Array[String]): Unit = {
        // keep only the errors
        val sparkConf = new SparkConf()
        .setMaster("local[*]")
        .setAppName("data_analysis")
        .set("spark.driver.host", "127.0.0.1")
        val sparkContext = new SparkContext(sparkConf)
        val spark: SparkSession = SparkSession.builder.config(sparkContext.getConf).getOrCreate()
        val conf = ConfigFactory.load()
        val accessKeyId = conf.getString("aws.accessKeyId")
        val secretAccessKey = conf.getString("aws.secretAccessKey")
        spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", accessKeyId)
        spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", secretAccessKey)
        spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")

        val obj = sparkContext.objectFile("s3a://inde2storage/" + "drone_1889_time_1687968104744.json").map((obj : SaveReport) =>
            SaveReport(
            obj.drone_id,
            obj.location,
            obj.persons,
            obj.words,
            obj.timestamp,
            ))

        val threshold = 0.1

        println("\n\n")
        val weekDays = obj.groupBy(x => x.timestamp.getDayOfWeek())
        println(s"Days of the week with the most pissed off people:")
        weekDays.foreach(x => println(s"${x._1} : ${x._2
            .map(event => {
                event.persons
                    .filter(person => person.harmonyScore < threshold)
                    .size })
            .reduce((x, y) => x + y)
        }"))

        println("\n\n")
        /*
        val agitationPerWords = obj.groupBy(x => x.words)
        println(s"Number of agitated person per words heard:")
        agitationPerWords.foreach(x => println(s"${x._1} : ${x._2
            .map(event => {
                event.persons
                    .filter(person => person.peacescore < threshold)
                    .size })
            .reduce((x, y) => x + y)
        }"))

        println("\n\n")
        val agitationPerPeacewatcher = obj.groupBy(x => x.peacewatcher_id)
        println(s"Agitation per peacewatcher id:")
        agitationPerPeacewatcher.foreach(x => println(s"${x._1} : ${x._2.size}"))

        println("\n\n")
        val agitationPerLocation = obj.groupBy(x =>
            (
                (x.location.latitude),
                (x.location.longitude)
            )
        )
        println(s"Agitation per location")
        agitationPerLocation.foreach(x => println(s"${x._1} : ${x._2.size}"))

        println("\n\n")
        val agitationPerHour = obj.groupBy(x => x.timestamp.getHour)
        println("Agitation per hour:")
        agitationPerHour.foreach(x => println(s"${x._1}h : ${x._2.size}"))*/
    }
}