package com.amrut.prabhu;

import com.amrut.prabhu.dto.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringCloudStreamKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
    }

    @Bean
    public Consumer<Message> consumer() {
        return message -> System.out.println("received " + message);
    }

    @Bean
    public Supplier<Message> producer() {
        return () -> new Message(" jack from Streams");
    }
}
