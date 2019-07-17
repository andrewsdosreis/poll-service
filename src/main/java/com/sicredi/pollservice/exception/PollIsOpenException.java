package com.sicredi.pollservice.exception;

public class PollIsOpenException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PollIsOpenException(Integer pollId, String topicName) {
        super(String.format("Poll Id '%s' for Topic '%s' is still open!\nCan't get Results of an opened Poll!", pollId, topicName));
    }
}