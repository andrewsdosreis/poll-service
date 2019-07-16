package com.sicredi.pollservice.messagebroker.producer;

import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class Producer<T> {

    @Log
    private Logger logger;

    @Autowired
    private AmqpTemplate amqpTemplate;

    abstract String getExchange();

    public void produce(T message) {
        logger.info("Poll is closed : %s", message);
        amqpTemplate.convertAndSend(getExchange(), "", message);
    }

}