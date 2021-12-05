package com.amrut.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerAssignAndSeek {

    public static Logger logger = LoggerFactory.getLogger(ConsumerAssignAndSeek.class);

    public static void main(String[] args) {
        String BOOT_STRAP_SERVER = "127.0.0.1:9092";

        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        //create kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);


        //assign a partition the topic
        TopicPartition topicPartition = new TopicPartition("amrut", 0);
        consumer.assign(Arrays.asList(topicPartition));


        //seek to an offset

        long offset = 5l;

        consumer.seek(topicPartition, offset);
        //read 4 records
        for (int recordsRead = 0; recordsRead < 4; recordsRead++) {

            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : consumerRecords) {

                logger.info("Record ---------------------------------------------------");
                logger.info("Partition:-" + record.partition());
                logger.info("Offset:-" + record.offset());
                logger.info("Key:-" + record.key());
                logger.info("Value:-" + record.value());


            }
        }

    }
}
