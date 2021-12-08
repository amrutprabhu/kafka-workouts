package com.amrut.prabhu.kafkacommunicationservice;

import com.amrut.prabhu.kafkacommunicationservice.dto.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "my-client-application", topics = "${topic.name}")
    public void consumer(ConsumerRecord<String, Message> consumerRecord) {
        System.out.println("Consumed Record Details: " + consumerRecord);
        Message message = consumerRecord.value();
        System.out.println("Consumed Message" + message);
    }
}
