package com.sicredi.pollservice.exception;

public class UserInvalidException extends ResourceNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserInvalidException(String userCpf) {
        super(String.format("User with CPF '%s' is invalid", userCpf));
    }

}