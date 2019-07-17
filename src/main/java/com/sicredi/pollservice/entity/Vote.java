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

import com.sicredi.pollservice.model.VoteOption;

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
    private VoteOption vote;

    public Vote() {
        super();
    }

    public Vote(Integer id, Poll poll, User user, VoteOption vote) {
        super();
        this.id = id;
        this.poll = poll;
        this.user = user;
        this.vote = vote;
    }

    public Vote(Poll poll, User user, VoteOption vote) {
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

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }

    public static class Builder {

        private Integer id;
        private Poll poll;
        private User user;
        private VoteOption vote;

        public Builder() {
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setPoll(Poll poll) {
            this.poll = poll;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setVote(VoteOption vote) {
            this.vote = vote;
            return this;
        }
        
        public Vote build() {
            return new Vote(id, poll, user, vote);
        }
    }
}