package alertHandler
import params._
import java.util.Properties
import play.api.libs.json._
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.apache.log4j.{Level, Logger}

object Main {
 def main(args: Array[String]): Unit = {
        val sparkConfig = new SparkConf()
            .setAppName("harmonyAlert")
            .setMaster("local[*]")
            .set("spark.driver.host", "127.0.0.1")
        val ssc = new StreamingContext(sparkConfig, Seconds(5))
        val kafkaParams = Map(
            "bootstrap.servers" -> "localhost:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "Alerts"
        )
        val topics = Array("dataPerson")
        val new_stream = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String,String](topics, kafkaParams)
        )

        val props = new Properties()
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
        
        val sparkContext = ssc.sparkContext
        val kafkaSink = sparkContext.broadcast(KafkaSink(props))
      
        new_stream.flatMap(report => {
            val json = Json.parse(report.value())
            Report.ReportFormatter.reads(json).asOpt
        })
        .filter(report => {
            val dangerous_persons = report.persons.filter(person => person.harmonyScore < 0.1)
            dangerous_persons.foreach(person => println(s"[ALERT] ${person.name} is dangerous with ${person.harmonyScore} as peacescore."))
            dangerous_persons.length != 0
        })
        .map({report_with_dangerous_person =>
                val all_alerts = report_with_dangerous_person.persons.filter(person=>person.harmonyScore<0.1).map(person_danger =>{
                val new_alert = Alert(
                report_with_dangerous_person.drone_id,
                report_with_dangerous_person.location,
                person_danger.name)
                val alertJsonString = Json.stringify(Json.toJson(new_alert))
                alertJsonString
                }
            )
            all_alerts
        })
        .foreachRDD({ rdd =>
            rdd.foreach({alerts =>{
              alerts.foreach(alert => {
                println(s"VOICI LE CONTENUE DE ALERT\n\n\n\n\n\n${alert}\n\n\n\n\n\n\n\n")
                kafkaSink.value.send("Alerts", "event_alert", alert)
              })
            }
          })
        })
        ssc.start()
        ssc.awaitTermination()
    }
}
