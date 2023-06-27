package s3_storage

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


object Main {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "drone-consumer-group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])

    val consumer = new KafkaConsumer[String, String](props)

    consumer.subscribe(Collections.singletonList("dataPerson"))

    val credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create(
      System.getenv("AKIA3KDI3IXQYTR5MBO7"),
      System.getenv("i6DKrAtnTkFRrIYo/RCdPi7NEc9FecfVldxTXUWo")
    ))

    // Create S3 client
    val s3 = S3Client.builder()
      .region(Region.EU_WEST_3)
      .credentialsProvider(credentials)
      .build()

    val bucketName = "inde2storage"

    try {
      while (true) {
        val records = consumer.poll(100)
        for (record <- records.asScala) {
          // Instead of printing, write to S3
          val content = s"Key: ${record.key()}, Value: ${record.value()}"

          val filePath = Paths.get("/tmp/data.txt")  // Replace with your file path
          Files.write(filePath, content.getBytes)

          s3.putObject(PutObjectRequest.builder().bucket(bucketName).key("data.txt").build(), filePath)
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
