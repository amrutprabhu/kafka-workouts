# Spring Boot Kafka Communication

##Commands

- Start Zookeeper
```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
```
- Start Kafka
```shell

```
- Create Kafka topic
```shell
bin/kafka-topics.sh --create \
--topic first-topic \
--partitions=4 \
--replication-factor=1 \
--bootstrap-server localhost:9092
```

- List Kafka Topics
```shell
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

- Console consumer
```shell
bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--property key.separator=: \
--property print.key=true \
--property print.offset=true \
--property print.partition=true \
--property print.value=true \
--property print.headers=true \
--topic first-topic \
--from-beginning \
--group group1
```

- Console Consumer
```shell
bin/kafka-console-producer.sh \
--bootstrap-server localhost:9092 \
--property parse.key=true \
--property key.separator=: \
--topic first-topic
```

- Payload
```json
mykey:"{\"name\": \"Jack\"}"
```
