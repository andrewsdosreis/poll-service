package com.sicredi.pollservice.exception;

public class PollAlreadyExistsException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PollAlreadyExistsException(String topicName) {
        super(String.format("Poll for Topic '%s' has already been created", topicName));
    }

}