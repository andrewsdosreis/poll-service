package com.sicredi.pollservice.exception;

public class UserAlreadyHasVotedForPollException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyHasVotedForPollException(String userCpf, String topicName) {
        super(String.format("User with CPF '%s' has already voted for Topic '%s'", userCpf, topicName));
    }

}