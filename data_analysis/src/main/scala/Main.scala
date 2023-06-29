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

        val obj = sparkContext.objectFile("s3a://inde2storage/" + "*" + ".json").map((obj : SaveReport) =>
            SaveReport(
            obj.drone_id,
            obj.location,
            obj.persons,
            obj.words,
            obj.timestamp,
            ))

        //NOT WORKING
    }
}