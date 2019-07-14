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
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Poll() {
        super();
    }

    public Poll(Integer id, Topic topic, LocalDateTime startDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Poll(Topic topic, LocalDateTime startDate, LocalDateTime endDate) {
        super();
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean isOpen() {
        return this.endDate.isBefore(LocalDateTime.now());
    }
    
}