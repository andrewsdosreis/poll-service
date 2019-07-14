package com.sicredi.pollservice.exception;

public class PollIsClosedException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PollIsClosedException(String topicName) {
        super(String.format("Poll for Topic '%s' has already been closed", topicName));
    }

}