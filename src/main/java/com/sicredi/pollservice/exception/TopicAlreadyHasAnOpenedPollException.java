package com.sicredi.pollservice.exception;

public class TopicAlreadyHasAnOpenedPollException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public TopicAlreadyHasAnOpenedPollException(String topicName) {
        super(String.format("Topic '%s' already has an opened Poll", topicName));
    }

}