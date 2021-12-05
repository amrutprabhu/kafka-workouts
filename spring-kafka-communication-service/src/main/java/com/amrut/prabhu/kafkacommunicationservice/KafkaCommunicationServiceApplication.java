package com.amrut.prabhu.kafkacommunicationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCommunicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCommunicationServiceApplication.class, args);
    }

}
