package com.sicredi.pollservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.model.request.OpenPollDto;
import com.sicredi.pollservice.model.response.PollDto;
import com.sicredi.pollservice.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private ObjectMapper mapper;
    private PollRepository pollRepository;
    private TopicService topicService;

    @Autowired
    public PollService(ObjectMapper mapper, PollRepository pollRepository, TopicService topicService) {
        this.mapper = mapper;
        this.pollRepository = pollRepository;
        this.topicService = topicService;
    }

    public Optional<Poll> findByTopic(Integer topicId) {
        return pollRepository.findByTopic_Id(topicId);
    }

    public Optional<PollDto> openPoll(OpenPollDto openPoll) {
        Optional<Topic> topic = topicService.findById(openPoll.getTopicId());

        Integer pollDurationInMinutes = setDurationInMinutes(openPoll.getDurationInMinutes());

        Poll poll = new Poll(topic.get(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(pollDurationInMinutes));

        return Optional.ofNullable(mapper.convertValue(pollRepository.save(poll), PollDto.class));
    }

    private Integer setDurationInMinutes(Integer durationInMinutes) {
        if (durationInMinutes == null || durationInMinutes == 0) {
            return 1;
        } else {
            return durationInMinutes;
        }
    }

}