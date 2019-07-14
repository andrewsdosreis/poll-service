package com.sicredi.pollservice.model.request;

public class OpenPollDto {

    private Integer topicId;
    private Integer durationInMinutes;

    public Integer getTopicId() {
        return topicId;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
    
}