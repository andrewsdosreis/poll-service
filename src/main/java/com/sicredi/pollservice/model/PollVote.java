package com.sicredi.pollservice.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class PollVote {

    private Integer topicId;
    private String userCpf;
    @Enumerated(EnumType.STRING)
    private VoteEnum vote;

    public Integer getTopicId() {
        return topicId;
    }

    public String getUserCpf() {
        return userCpf;
    }
   
    public VoteEnum getVote() {
        return vote;
    }

}