package s3Storage

import java.util.Properties
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import scala.collection.JavaConverters._
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import java.nio.file.Paths
import java.util.Collections
import java.nio.file.Files
import play.api.libs.json.{JsValue, Json}
import java.io.ByteArrayInputStream
import software.amazon.awssdk.core.sync.RequestBody
import com.typesafe.config.ConfigFactory

object Main {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "drone-consumer-group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])

    val consumer = new KafkaConsumer[String, String](props)

    consumer.subscribe(Collections.singletonList("dataPerson"))
    
    val conf = ConfigFactory.load()
    val accessKeyId = conf.getString("aws.accessKeyId")
    val secretAccessKey = conf.getString("aws.secretAccessKey")

    val credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(
      accessKeyId,
      secretAccessKey
    ))

    // Create S3 client
    val s3 = S3Client.builder()
      .region(Region.EU_WEST_3)
      .credentialsProvider(credentials)
      .build()

    val bucketName = "inde2storage"

    try {
      val iter = Iterator.continually {
        val records = consumer.poll(100)
        records.asScala.map { record =>
          val json: JsValue = Json.parse(record.value())
          val jsonString: String = Json.stringify(json)
          val stream = new ByteArrayInputStream(jsonString.getBytes)
          val drone_id: String = (json \ "drone_id").as[Long].toString
          val timestamp: String = (json \ "timestamp").as[String]
          val fileName = "drone_" + drone_id + "_time_" + timestamp + ".json"
          s3.putObject(
            PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
            RequestBody.fromInputStream(stream, jsonString.length) 
          )
          true
        }
      }
      iter.takeWhile(_.forall(identity)).foreach(_ => ())
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
