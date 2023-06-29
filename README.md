# INDE2_FinalProject

The goal of this project is to create a program that will receive, manage and analyse data from harmonywatchers, which are drones that scan their surroundings and check if citizens are happy or not.

We chose this architecture from the program that we created :

![Architecture](https://github.com/Exared/INDE2_FinalProject/assets/89941855/5f26f93b-c75b-4019-bfb8-1f97e217413f)


Our Project contains 7 folders :
- alert_handler
- api_alerts
- data_analysis
- front-end
- generating_data
- params
- s3_storage

## alert_handler
In this folder you will find the Main that is used to find the citizens with a bad harmony score from the generated data by the harmonywatchers and send alerts that we then use in the api.

## api_alerts
In this folder you will find everything related to the creation of an API to permit the front-end to have access to the data that is stored in Kafka's STREAM. Please read the README in this directory.
    
## front-end
In this folder you will find every file concerning the creation of our front-end to permit the harmonyWatchers to have access to all the alerts and the statistics. Please read the README in this directory.

## params
In this folder you will find the declaration of our classes that are used in the alerts and drone simulation. You will also find the Json formatter for each one of them.

## generating_data
In this folder you will find the Main that is used to generate data which simulates the harmonywatchers scanning the city and sending information that they are getting.
    
## 5 files :
- build.sbt
- README.md
- Architecture.png
- SoutenanceFinale.pptx
- .gitignore

# Usage

1. Launch your Zookeeper server
2. Launch your Kafka
3. Execute "sbt \"project generating_data\" run" to run the data generator that will send it to a kafka streams with the topic dataPerson as a kafka producer
4. Execute "sbt \"project alert_handler\" run" to run the alert generator it will connect to the kafka streams (dataPerson) as a consumer and be a producer of its own kafka stream named Alert and it will thanks to spark do a data manipulation on data coming from dataPerson. For every person that has a harmonyScore <0.1 it will create a new Alert component and send it to the kafka streams named Alerts 
5. Execute "sbt \"project s3_storage\" run". In this part we are trying to get every informat
6. Execute "sbt \"project data_analysis\" run". In this part we are trying to get every data from S3's bucket and do data manipulation using spark.The provided code performs an analysis on a dataset represented by the object obj. It groups the data by weekdays and calculates the number of "pissed off" individuals for each day. The code outputs a summary showing the days of the week with the highest count of discontented people.
First, the data is grouped by the day of the week using the getDayOfWeek() method of the timestamp attribute. This creates a new object where the records are grouped based on weekdays. Then, the code iterates over each group and calculates the number of discontented people for that day.
For each group, it applies a transformation to each record, filtering out individuals who have a harmonyScore below a certain threshold (indicating discontent). The code then counts the number of filtered individuals for that day and sums them up using the reduce operation. Finally, the code prints the results, displaying the day of the week along with the corresponding count of discontented people.
This analysis provides insights into the distribution of discontented individuals across different days of the week, allowing for further investigation and understanding of potential patterns or factors influencing people's dissatisfaction.