package com.sicredi.pollservice.model;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class VoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private PollDto poll;
    private UserDto user;
    @Enumerated(EnumType.STRING)
    private VoteEnum vote;

    public VoteDto() {
        super();
    }

    public VoteDto(Integer id, PollDto poll, UserDto user, VoteEnum vote) {
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

    public VoteEnum getVote() {
        return vote;
    }

}