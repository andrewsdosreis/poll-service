package com.sicredi.pollservice.model.request;

public class CreatePollDto {

    private Integer topicId;
    private Integer durationInMinutes;

    public CreatePollDto(Integer topicId, Integer durationInMinutes) {
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