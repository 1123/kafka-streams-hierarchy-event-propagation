# Kafka Streams Spring Boot App Hierachary Stream-Table Join

## Introduction 

In many IOT use cases such as transportation, manufacturing and smart cities, events must be inherited from a set of elements to all its members. 
Often these sets are of a hierarchical structure, and are dynamically updated, i.e. members are added or removed from the set continuously. 
Kafka Streams is arguably the most popular event streaming technology used to implement these use cases. 

This repository shows how to generate events for all elements in a dynamic, tree-structured set upon arrival of an event for the entire set using Kafka Streams. 
In essence, the tree structure is maintained in a Kafka Streams KTable, and the events are joined to the tree structure using a Kafka Streams Stream-Table join. 
Subsequently, the event is forwarded downstream for each of the leaf nodes of the hierarchy. 

## How to run this Demo

### Prerequisites

* Sign up for Confluent Cloud (it's free) and generate an Api Key and Api Secret. 
  Alternatively, you can spin up a local Kafka cluster by downloading a recent version of Confluent Platform. 

* Recent installation of a JDK. This demo was tested with Java 16. 

* mvn for building and running the applications

### Running the demo

* edit the file 'producer-app/src/main/resources/application.yml' to include your Confluent Cloud API Endpoint, API Key and API Secret. 

* create the following topics in your Confluent Cloud Cluster: trees, tree-events, leaf-events, streams-tree-join-app-trees-STATE-STORE-0000000000-changelog . 
  The last topic is used to persist the state of the tree structures across restarts of the streams application. 

* run the producer application: 'cd producer-app; mvn spring-boot:run'. Keep this application running and open a new terminal for the following steps. 

* edit the file 'strams-app/src/main/resources/application.yml' to include your Confuent Cloud API Endpoint, API Key and API Secret.

* run the streams application: 'cd streams-app; mvn spring-boot:run'. Also keep this application running. 

* Inspect the logs of both applications, and the watch the events flowing through the topics in the Confluent Cloud UI. 

In case of any errors or questions, please submit an issue in this repository. 

When running a local Kafka cluster, insertion of API keys is not needed. Instead use the application-local.yml configuration files by setting 'export SPRING_PROFILES_ACTIVE=local' before running the applications. 
