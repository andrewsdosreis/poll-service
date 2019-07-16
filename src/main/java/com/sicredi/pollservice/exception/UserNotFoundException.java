package com.sicredi.pollservice.exception;

public class UserNotFoundException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String userCpf) {
        super(String.format("User with CPF '%s' has not been found", userCpf));
    }

    public UserNotFoundException(Integer userId) {
        super(String.format("User with Id '%s' has not been found", userId));
    }
}