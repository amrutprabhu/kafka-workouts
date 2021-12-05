package com.amrut.prabhu.kafkacommunicationservice;

import com.amrut.prabhu.kafkacommunicationservice.dto.Message;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaProducer {

    @Value("${topic.name}")
    private String topicName;

    private KafkaTemplate kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage() {
        UUID key = UUID.randomUUID();
        Message payload = new Message("jack");
        System.out.println("Sending Data " + payload);

        ProducerRecord<String, Message> record = new ProducerRecord<String, Message>(topicName,
                key.toString(),
                payload);
        record.headers()
                .add("message-id", UUID.randomUUID()
                        .toString()
                        .getBytes());
        kafkaTemplate.send(record);
    }
}
