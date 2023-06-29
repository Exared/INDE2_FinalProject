# INDE2_FinalProject

The goal of this project is to create a program that will receive, manage and analyse data from harmonywatchers, which are drones that scan their surroundings and check if citizens are happy or not.

We chose this architecture from the program that we created :

![Architecture](https://github.com/Exared/INDE2_FinalProject/assets/89941855/5f26f93b-c75b-4019-bfb8-1f97e217413f)


Our Project contains 3 folders :
    -api
    -front-end
    -src

## api
    In this folder you will find everything related to the creation of an API to permit the front-end to have access to the data that
    is stored in Kafka's STREAM. Please read the README in this directory.
## front-end
    In this folder you will find every files conercing the creation of our front-end to permit the harmonyWatchers to have access to all the alerts and the statistics. Please read the README in this directory.

## src
    It contains every scala file that permits us to manage this data structure
2 files :
    -build.sbt
    -README.md

# Usage

1. Launch your Zookeeper server
2. Launch your Kafka
3. Type "sbt run" and choose the droneSimulator main
4. Then re-run "sbt run" and choose the alertHandler main
5. 
