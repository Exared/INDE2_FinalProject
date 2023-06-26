package droneSimulator

import data._

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._
import java.util.Properties
import play.api.libs.json._
import java.time.LocalDateTime
import scala.util.Random
import scala.io.Source
import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.StringSerializer


object Main {
    def generateData(names: JsArray, phrases: List[String], producer: KafkaProducer[String, String]) = {
        (0 to 5).foreach((i) => {
            val event = Report(
                Random.between(0, 10000),
                Location(Random.between(-90.0, 90.0), Random.between(-180.0, 180.0)),
                (1 to Random.between(1, 10)).map(_ =>
                    {
                        val person = Random.between(0, 10000)
                        Person(
                            (names.value(person) \ "name").as[String],
                            (names.value(person) \ "happiness_score").as[Double]
                        )
                    }).toList,
                (1 to Random.between(1, 5)).map(p => phrases(Random.nextInt(phrases.size))).toList,
                LocalDateTime.now()
            )
            val eventJsonString = Json.stringify(Json.toJson(event))
            println(eventJsonString)
            val record = new ProducerRecord[String, String]("inde2_project", "event", eventJsonString)
            producer.send(record)
            Thread.sleep(1000)
        })
    }

  def main(args: Array[String]): Unit = {
        
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    val producer : KafkaProducer[String, String] = new KafkaProducer[String, String](props)
    val namesFileName = "src/main/scala/inde2_project/data/people.json"
    val fSource = Source.fromFile(namesFileName)
    val namesRaw = fSource.getLines.mkString
    fSource.close()
    val namesJson = Json.parse(namesRaw)    
    val phrases = List("peace", "love", "harmony", "happiness", "joy", "respect", "tolerance", "solidarity", "equality", "freedom", "justice", "dignity", "non-violence", "non-violent", "nonviolence", "violent", "violence", "hello", "hi", "goodbye", "bye", "good morning", "good afternoon")
    generateData(namesJson.as[JsArray], phrases, producer)
    println("GENERATING AND SENDING DATA DONE")
    producer.close()    
    val props2 = new Properties()
    props2.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092") // Adresse et port du broker Kafka
    props2.put(ConsumerConfig.GROUP_ID_CONFIG, "drone-consumer-group") // ID du groupe de consommateurs
    props2.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer]) // Désérialiseur de clés
    props2.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer]) // Désérialiseur de valeurs

    val consumer = new KafkaConsumer[String, String](props2)
    println("Subscribing to data")
    consumer.subscribe(java.util.Collections.singletonList("inde2_project"))
    println("Getting data Done")

    while (true) {
      println("printing data")
      val records = consumer.poll(100) // Récupère les enregistrements du topic
      for (record <- records.asScala) {
        val key = record.key()
        val value = record.value()
        println(s"Clé: $key, Valeur: $value")
      }
    }

    consumer.close()
    }
}