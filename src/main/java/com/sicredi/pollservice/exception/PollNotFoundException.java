package com.sicredi.pollservice.exception;

public class PollNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PollNotFoundException(Integer topicId) {
        super(String.format("Poll with Topic Id '%s' has not been found", topicId));
    }

}