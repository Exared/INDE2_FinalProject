const axios = require('axios')
const { Kafka } = require('kafkajs')
const kafka = new Kafka({
  clientId: 'npm-slack-notifier',
  brokers: [`localhost:9092`]
})
const consumer = kafka.consumer({
  groupId: "alert"
})

const main = async () => {
  await consumer.connect()

  await consumer.subscribe({
    topic: "Alerts",
    fromBeginning: true
  })
  console.log("subing")
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log('Received message', {
        topic,
        partition,
        key: message.key.toString(),
        value: message.value.toString()
      })
    console.log("ruiing")
      let alert = JSON.parse(message.value);
        axios.post('http://localhost:4000/alert', { drone_id:alert.drone_id, person_name: alert.person_name, drone_location: alert.location })
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
     
  
    }
  })
}

main().catch(async error => {
  console.error(error)
  try {
    await consumer.disconnect()
  } catch (e) {
    console.error('Failed to gracefully disconnect consumer', e)
  }
  process.exit(1)
})