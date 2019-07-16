package com.sicredi.pollservice.messagebroker.producer;

import com.sicredi.pollservice.model.response.PollResultDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PollResultProducer extends Producer<PollResultDto> {

    @Value("${rabbitmq.PollResult.exchange}")
	private String exchange;

	@Override
	String getExchange() {
		return exchange;
    }

}