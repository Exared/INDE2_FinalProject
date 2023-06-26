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
import faker._


object Main {
    /* Generate json containing ID, location, list of persons, list of words, timestamp :
    {
      peacewatcher_id: 1,
      location: {
        latitude: 0.0,
        longitude: 0.0
      },
      persons: [
        {
          name: "John Doe",
          harmonyScore: 0.5
        },
        ...
      ],
      words: [
        "peace",
        "love",
        "harmony",
        ...
      ],
      timestamp: "2021-04-01T12:00:00.000"
    }
    On envoie ces json sur le topic "inde2_project" du broker Kafka
    */
    def generateData(producer: KafkaProducer[String, String]) = {
        val random_words = List("peace", "love", "harmony", "happiness", "joy", "respect", "tolerance", "solidarity", "equality", "freedom", "justice", "dignity", "non-violence", "non-violent", "nonviolence", "violent", "violence", "hello", "hi", "goodbye", "bye", "good morning", "good afternoon") 
        (0 to 1000).foreach((i) => {
            val event = Report(
                Random.between(0, 10000),
                Location(Random.between(-90.0, 90.0), Random.between(-180.0, 180.0)),
                (1 to Random.between(1, 50)).map(_ =>
                        Person(
                            name = Faker.default.fullName(),
                            harmonyScore = Random.nextDouble()
                        )).toList,
                (1 to Random.between(1, 10)).map(p => random_words(Random.nextInt(random_words.size))).toList,
                LocalDateTime.now()
            )
            val eventJsonString = Json.stringify(Json.toJson(event))
            println(eventJsonString)
            val record = new ProducerRecord[String, String]("inde2_project", "event", eventJsonString)
            producer.send(record)
            Thread.sleep(60000)
        })
    }

  def main(args: Array[String]): Unit = {
        
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    val producer : KafkaProducer[String, String] = new KafkaProducer[String, String](props)

    generateData(producer)
    producer.close()
    }
}