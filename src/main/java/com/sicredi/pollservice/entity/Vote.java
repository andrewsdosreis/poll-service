package com.sicredi.pollservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sicredi.pollservice.model.VoteEnum;

@Entity
@Table(name = "vote", schema = "poll")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteEnum vote;

    public Vote() {
        super();
    }

    public Vote(Integer id, Poll poll, User user, VoteEnum vote) {
        super();
        this.id = id;
        this.poll = poll;
        this.user = user;
        this.vote = vote;
    }

    public Vote(Poll poll, User user, VoteEnum vote) {
        super();
        this.poll = poll;
        this.user = user;
        this.vote = vote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteEnum getVote() {
        return vote;
    }

    public void setVote(VoteEnum vote) {
        this.vote = vote;
    }

}