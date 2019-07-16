package com.sicredi.pollservice.model.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PollDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private TopicDto topic;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime endDate;

    public PollDto() {
        super();
    }

    public PollDto(Integer id, TopicDto topic, LocalDateTime startDate, LocalDateTime endDate) {
        super();
        this.id = id;
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public TopicDto getTopic() {
        return topic;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Poll [, id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", topic=" + topic.getName() + "]";
    }

}