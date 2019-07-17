package com.sicredi.pollservice.exception;

public class UserIsUnableToVoteException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public UserIsUnableToVoteException(String userCpf) {
        super(String.format("User with CPF '%s' is unable to vote", userCpf));
    }

}