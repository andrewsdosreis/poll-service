package com.sicredi.pollservice.model.request;

public class OpenPollDto {

    private Integer topicId;
    private Integer durationInMinutes;

    public OpenPollDto(Integer topicId, Integer durationInMinutes) {
        this.topicId = topicId;
        this.durationInMinutes = durationInMinutes;
    }    

    public Integer getTopicId() {
        return topicId;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
   
}