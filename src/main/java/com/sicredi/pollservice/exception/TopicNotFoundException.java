package com.sicredi.pollservice.exception;

public class TopicNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public TopicNotFoundException(Integer topicId) {
        super(String.format("Topic with Id '%s' has not been found", topicId));
    }

}