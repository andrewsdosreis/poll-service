package com.sicredi.pollservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "poll", schema = "poll")
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "closed")
    private Boolean closed;

    public Poll() {
        super();
    }

    public Poll(Integer id, Topic topic, LocalDateTime startDate, LocalDateTime endDate, Boolean closed) {
        super();
        this.id = id;
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closed = closed;
    }

    public Poll(Topic topic, LocalDateTime startDate, LocalDateTime endDate, Boolean closed) {
        super();
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.closed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getClosed() {
        return this.closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public boolean isOpen() {
        return this.endDate.isAfter(LocalDateTime.now());
    }

    public String getTopicName() {
        return this.getTopic().getName();
    }

    public static class Builder {

        private Integer id;
        private Topic topic;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Boolean closed;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setTopic(Topic topic) {
            this.topic = topic;
            return this;
        }

        public Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setClosed(Boolean closed) {
            this.closed = closed;
            return this;
        }

        public Builder() {
        }

        public Poll build() {
            return new Poll(id, topic, startDate, endDate, closed);
        }
    }
}