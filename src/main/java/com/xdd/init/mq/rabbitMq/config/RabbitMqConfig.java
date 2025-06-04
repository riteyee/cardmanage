package com.xdd.init.mq.rabbitMq.config;

import com.xdd.init.mq.rabbitMq.constents.RabbitMqContents;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     *  声明交换机
     */
//    @Bean(RabbitMqContents.EXCHANGE_NAME)
//    public Exchange exchange(){
//        //durable(true) 持久化，mq重启之后交换机还在
//        return ExchangeBuilder.topicExchange(RabbitMqConstants.EXCHANGE_NAME).durable(true).build();
//    }
}
