# This folder contains everything that will permite us to create an API that get data from Kafka streams and send it to the front-end

## api_consumer.js
Contains the script that get value from KafkaStreams using kafkajs and sends it to the redirected server

## server.js
Contain the redirected server where the front-end will send à GET request to get data

Please run :
    - npm install (to install all the packages needed to launch your app)
    - node api_consumer.js (to start the api_consumer)
    - node server.js (to start the redirected server)
    