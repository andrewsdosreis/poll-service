package com.sicredi.pollservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PollDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private TopicDto topic;
    private LocalDateTime startDate;
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

}