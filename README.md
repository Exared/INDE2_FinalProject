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

## alert_handle
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
3. Type "sbt run" and choose the droneSimulator main
4. Then re-run "sbt run" and choose the alertHandler main
5. 
