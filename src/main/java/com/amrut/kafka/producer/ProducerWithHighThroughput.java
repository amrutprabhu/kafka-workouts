package com.amrut.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithHighThroughput {

    public static void main(String[] args) {
        //Kafka properties
        Logger log = LoggerFactory.getLogger(ProducerWithHighThroughput.class);
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Safe producer properties
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // safe for kafka 2.0 or else use 1
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));

        // high throughput properties
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,Integer.toString(32*1024));   //32KB
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG,"10"); //10 ms
        
        // kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // kafka record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("amrut", "key", "value");

        // send record -- async
        producer.send(record, (rcdMetaData, exp) -> {
            // call every successful send or in case of exception

            if (exp == null) {
                log.info("Offset:- " + rcdMetaData.offset());
                log.info("Partition:- " + rcdMetaData.partition());
                log.info("Timestamp:- " + rcdMetaData.timestamp());
            } else {
                log.error("error", exp);
            }
        });

        //flush data
        producer.flush();

        //close producer
        producer.close();
    }
}
