package com.amrut.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {
        //Kafka properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // kafka producer
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        // kafka record
        ProducerRecord<String, String> record= new ProducerRecord<String, String>("amrut","message");

        // send record -- async
        producer.send(record);

        //flush data
        producer.flush();

        //close producer
        producer.close();
    }
}
