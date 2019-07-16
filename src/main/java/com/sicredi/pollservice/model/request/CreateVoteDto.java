package com.sicredi.pollservice.model.request;

public class CreateVoteDto {

    private Integer pollId;
    private String userCpf;
    private String vote;

    public CreateVoteDto(Integer pollId, String userCpf, String vote) {
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

    public String getVote() {
        return vote;
    }

}