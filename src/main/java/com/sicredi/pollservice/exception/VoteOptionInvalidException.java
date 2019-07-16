package com.sicredi.pollservice.exception;

public class VoteOptionInvalidException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public VoteOptionInvalidException(String voteOption) {
        super(String.format("Vote option '%s' is invalid! Please choose between YES or NO", voteOption));
    }

}