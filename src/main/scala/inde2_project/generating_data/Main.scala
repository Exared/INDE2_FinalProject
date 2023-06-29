package droneSimulator

import params._

import java.util.Properties
import play.api.libs.json._
import java.time.LocalDateTime
import scala.util.Random
import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.StringSerializer
import faker._

object Main {
    def generateData(producer: KafkaProducer[String, String]) = {
        val random_words = List("peace", "love", "harmony", "happiness", "joy", "respect", "tolerance", "solidarity", "equality", "freedom", "justice", "dignity", "non-violence", "non-violent", "nonviolence", "violent", "violence", "hello", "hi", "goodbye", "bye", "good morning", "good afternoon") 

        val iter = Iterator.continually {
            val event = Report(
                Random.between(0, 10000),
                Location(Random.between(-90.0, 90.0), Random.between(-180.0, 180.0)),
                (1 to Random.between(1, 50)).map(_ =>
                    Person(
                        name = Faker.default.fullName(),
                        harmonyScore = Random.nextDouble()
                    )
                ).toList,
                (1 to Random.between(1, 10)).map(p => random_words(Random.nextInt(random_words.size))).toList,
                LocalDateTime.now()
            )

            val eventJsonString = Json.stringify(Json.toJson(event))
            println(eventJsonString)
            val record = new ProducerRecord[String, String]("dataPerson", "event", eventJsonString)
            producer.send(record)
            Thread.sleep(60000)
            true
        }
        iter.takeWhile(identity).foreach(_ => ())
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