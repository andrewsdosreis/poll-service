package com.sicredi.pollservice.exception;

public class TopicAlreadyExistsException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public TopicAlreadyExistsException(String topicName) {
        super(String.format("Topic '%s' has already been created", topicName));
    }

}