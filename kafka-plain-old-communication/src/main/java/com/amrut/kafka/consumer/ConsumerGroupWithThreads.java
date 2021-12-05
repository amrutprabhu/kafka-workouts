package com.amrut.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerGroupWithThreads {

    public static Logger logger = LoggerFactory.getLogger(ConsumerGroupWithThreads.class);


    public static void main(String[] args) {

        new ConsumerGroupWithThreads().run();
    }

    public void run() {
        String MY_GROUP_ID = "My_App_2";
        String BOOT_STRAP_SERVER = "127.0.0.1:9092";
        ConsumerThread consumerThread = new ConsumerThread(BOOT_STRAP_SERVER, MY_GROUP_ID);

        Thread thread = new Thread(consumerThread);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumerThread.shutdown();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }));

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("thread is interrupted");
        }

        logger.info("Main is exiting");
    }

    class ConsumerThread implements Runnable {

        private Logger logger = LoggerFactory.getLogger(ConsumerThread.class);
        private final String MY_GROUP_ID;
        private final String BOOT_STRAP_SERVER;
        private KafkaConsumer<String, String> consumer;

        public ConsumerThread(String BOOT_STRAP_SERVER,
                              String MY_GROUP_ID) {
            this.BOOT_STRAP_SERVER = BOOT_STRAP_SERVER;
            this.MY_GROUP_ID = MY_GROUP_ID;
        }

        @Override
        public void run() {

            Properties properties = new Properties();

            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOT_STRAP_SERVER);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, MY_GROUP_ID);
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


            //create kafka consumer
            consumer = new KafkaConsumer<String, String>(properties);


            //subscribe the topic
            consumer.subscribe(Collections.singleton("amrut"));


            try {
                //read records
                while (true) {

                    ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record : consumerRecords) {

                        logger.info("Record ---------------------------------------------------");
                        logger.info("Partition:-" + record.partition());
                        logger.info("Offset:-" + record.offset());
                        logger.info("Key:-" + record.key());
                        logger.info("Value:-" + record.value());
                    }
                }

            } catch (WakeupException e) {
                logger.error("Wakeup exception for shutdown");
            } finally {
                consumer.close();
                logger.info("consumer closing");
            }
        }

        public void shutdown() {
            // causes the poll function to throw a wakeup exception.
            consumer.wakeup();
        }
    }
}
