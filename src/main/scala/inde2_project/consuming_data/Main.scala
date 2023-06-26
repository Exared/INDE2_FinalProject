import java.time.Duration
import java.util.Properties
import java.util.Collections
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import scala.collection.JavaConverters._


object ConsumerMain {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "drone-consumer-group")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])

    val consumer = new KafkaConsumer[String, String](props)

    consumer.subscribe(Collections.singletonList("inde2_project"))

    try {
      while (true) {
        val records = consumer.poll(100)
        for (record <- records.asScala) {
          println(s"Key: ${record.key()}, Value: ${record.value()}")
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }
}
