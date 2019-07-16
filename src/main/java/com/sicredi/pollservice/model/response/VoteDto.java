package com.sicredi.pollservice.model.response;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sicredi.pollservice.model.VoteOption;

public class VoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private PollDto poll;
    private UserDto user;
    @Enumerated(EnumType.STRING)
    private VoteOption vote;

    public VoteDto() {
        super();
    }

    public VoteDto(Integer id, PollDto poll, UserDto user, VoteOption vote) {
        super();
        this.id = id;
        this.poll = poll;
        this.user = user;
        this.vote = vote;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public PollDto getPoll() {
        return poll;
    }

    public UserDto getUser() {
        return user;
    }

    public VoteOption getVote() {
        return vote;
    }

    @Override
    public String toString() {
        return "Vote [id=" + id + ", poll=" + poll.getId() + ", topic=" + poll.getTopic().getName() + ", user=" + user.getCpf() + ", vote=" + vote + "]";
    }

}