package com.sicredi.pollservice.model.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sicredi.pollservice.model.VoteOption;

public class PollVote {

    private Integer topicId;
    private String userCpf;
    @Enumerated(EnumType.STRING)
    private VoteOption vote;

    public PollVote(Integer topicId, String userCpf, VoteOption vote) {
        this.topicId = topicId;
        this.userCpf = userCpf;
        this.vote = vote;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getUserCpf() {
        return userCpf;
    }
   
    public VoteOption getVote() {
        return vote;
    }

}