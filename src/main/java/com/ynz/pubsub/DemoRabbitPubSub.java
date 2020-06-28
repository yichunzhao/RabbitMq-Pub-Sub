package com.ynz.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoRabbitPubSub {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoRabbitPubSub.class, args);
    }
}
