package com.sicredi.pollservice.model.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sicredi.pollservice.model.VoteOption;

public class CreateVoteDto {

    private Integer pollId;
    private String userCpf;
    @Enumerated(EnumType.STRING)
    private VoteOption vote;

    public CreateVoteDto(Integer pollId, String userCpf, VoteOption vote) {
        this.pollId = pollId;
        this.userCpf = userCpf;
        this.vote = vote;
    }

    public Integer getPollId() {
        return pollId;
    }

    public String getUserCpf() {
        return userCpf;
    }
   
    public VoteOption getVote() {
        return vote;
    }

}