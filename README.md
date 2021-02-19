# Apache Kafka client examples

This folder contains examples of Apache Kafka clients written using the Apache Kafka Java APIs:
* Message Producer which periodically produces messages into a topic
* Streams application which reads messages from a topic, transforms them (reverses the message payload) and sends them to another topic
* Consumer which is consuming messages from a topic

All examples are assembled into Docker images which allows them to be deployed on Kubernetes or OpenShift.

This repository contains `Deployments` for the clients as well as `KafkaTopic` and `KafkaUsers` for use by operators.
Logging configuration can be found in the `log4j2.properties` file for the producer and consumer separately.

## Build

To build these examples you need some basic requirements.
Make sure you have `make`, `docker`, `JDK 11` and `mvn` installed. 

From command line in root folder project, export your docker registry information as environment variable, bellow an exemple:

```
export DOCKER_REGISTRY=docker.io
export USER=mrabbah
export DOCKER_TAG=latest
export DOCKER_VERSION_ARG=latest
```

If you set environment variables like this, it's gonna mean you will have image build with this tag: mrabbah/$(PROJECT_NAME):latest
After that the image wille be tagged as : docker.io/mrabbah/$(PROJECT_NAME):latest

By one single command Java sources are compiled into JAR files, Docker images are created and pushed to repository.
By default the Docker organization to which images are pushed is the one defined by the `USER` environment variable which is assigned to the `DOCKER_ORG` one.
The organization can be changed exporting a different value for the `DOCKER_ORG` and it can also be the internal registry of an OpenShift running cluster.

The command for building the examples is:

```
make all
```

## Usage

After successfully building the images (which will cause the images to be pushed to the specified Docker repository) you are ready to deploy the producer and consumer containers along with Kafka and Zookeper.

To run this examples you must create Topics by applying [`create_topics_ssl_auth.yaml`](./java/kafka/create_topics_ssl_auth.yaml)
And you can deploy the examples by applying [`deploy_apps_ssl_auth.yaml`](./java/kafka/deploy_apps_ssl_auth.yaml).
This will create Kubernetes `Deployments` with the example image.

## Configuration

Although this Hello World is simple example it is fully configurable.
Below are listed and described environmental variables.

Producer  
* `BOOTSTRAP_SERVERS` - comma-separated host and port pairs that is a list of Kafka broker addresses. The form of pair is `host:port`, e.g. `my-cluster-kafka-bootstrap:9092` 
* `TOPIC` - the topic the producer will send to  
* `DELAY_MS` - the delay, in ms, between messages  
* `MESSAGE_COUNT` - the number of messages the producer should send  
* `CA_CRT` - the certificate of the CA which signed the brokers' TLS certificates, for adding to the client's trust store
* `USER_CRT` - the user's certificate
* `USER_KEY` - the user's private key
* `LOG_LEVEL` - logging level  
* `PRODUCER_ACKS` = acknowledgement level
* `HEADERS` = custom headers list separated by commas of `key1=value1, key2=value2`
* `ADDITIONAL_CONFIG` = additional configuration for a producer application. The form is `key=value` records separated by new line character
* `BLOCKING_PRODUCER` = if it's set, the producer will block another message until ack will be received
* `MESSAGES_PER_TRANSACTION` = how many messages will be part of one transaction. Transaction config could be set via `ADDITIONAL_CONFIG` variable. Default is 10.

Consumer  
* `BOOTSTRAP_SERVERS` - comma-separated host and port pairs that is a list of Kafka broker addresses. The form of pair is `host:port`, e.g. `my-cluster-kafka-bootstrap:9092` 
* `TOPIC` - name of topic which consumer subscribes  
* `GROUP_ID` - specifies the consumer group id for the consumer
* `MESSAGE_COUNT` - the number of messages the consumer should receive
* `CA_CRT` - the certificate of the CA which signed the brokers' TLS certificates, for adding to the client's trust store
* `USER_CRT` - the user's certificate
* `USER_KEY` - the user's private key
* `LOG_LEVEL` - logging level  
* `ADDITIONAL_CONFIG` = additional configuration for a consumer application. The form is `key=value` records separated by new line character

Streams  
* `BOOTSTRAP_SERVERS` - comma-separated host and port pairs that is a list of Kafka broker addresses. The form of pair is `host:port`, e.g. `my-cluster-kafka-bootstrap:9092`
* `APPLICATION_ID` - The Kafka Streams application ID
* `SOURCE_TOPIC` - name of topic which will be used as the source of messages
* `TARGET_TOPIC` - name of topic where the transformed images are sent
* `COMMIT_INTERVAL_MS` - the interval for the Kafka Streams consumer part committing the offsets
* `CA_CRT` - the certificate of the CA which signed the brokers' TLS certificates, for adding to the client's trust store
* `USER_CRT` - the user's certificate
* `USER_KEY` - the user's private key
* `LOG_LEVEL` - logging level
* `ADDITIONAL_CONFIG` = additional configuration for a streams application. The form is `key=value` records separated by new line character
