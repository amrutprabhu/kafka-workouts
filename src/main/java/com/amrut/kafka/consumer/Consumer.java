package com.amrut.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {

    public static Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        String MY_GROUP_ID = "My_App";
        String BOOT_STRAP_SERVER = "127.0.0.1:9092";

        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, MY_GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        //create kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);


        //subscribe the topic
        consumer.subscribe(Collections.singleton("amrut"));


        //read records
        while(true) {

            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String,String> record : consumerRecords){

                logger.info("Record ---------------------------------------------------");
                logger.info("Partition:-"+record.partition());
                logger.info("Offset:-"+record.offset());
                logger.info("Key:-"+record.key());
                logger.info("Value:-"+record.value());
            }
        }

    }
}
