package com.ynz.pubsub;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoRabbitPubSub {
    static final String topicExchangeName = "spring-boot-exchange";
    static final String queueName = "MyAnotherQueue";
    static final String routingKey ="order";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoRabbitPubSub.class, args);
    }

    /**
     * Define a message Queue on the Rabbit MQ sever.
     * @return Queue
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /**
     * Define the exchange to use.
     * @return TopicExchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**
     *  binding the exchange to a queue with a routing key.
     * @return Binding
     */
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Subscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

}
