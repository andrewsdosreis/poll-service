package com.sicredi.pollservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyHasAnOpenedPollException;
import com.sicredi.pollservice.exception.PollNotFoundException;
import com.sicredi.pollservice.model.request.CreatePollDto;
import com.sicredi.pollservice.model.response.PollDto;
import com.sicredi.pollservice.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    @Log
    private Logger logger;
    
    private ObjectMapper mapper;
    private PollRepository pollRepository;
    private TopicService topicService;

    @Autowired
    public PollService(ObjectMapper mapper, PollRepository pollRepository, TopicService topicService) {
        this.mapper = mapper;
        this.pollRepository = pollRepository;
        this.topicService = topicService;
    }

    public Optional<Poll> findById(Integer id) {
        Optional<Poll> poll = pollRepository.findById(id);
        checkIfPollNotFound(poll, id);
        return poll;
    }

    public Optional<PollDto> create(CreatePollDto createPoll) {
        Optional<Topic> topic = topicService.findById(createPoll.getTopicId());
        checkIfTopicAlreadyHasAnOpenedPoll(createPoll);
        Integer pollDurationInMinutes = setDurationInMinutes(createPoll.getDurationInMinutes());
        Poll poll = new Poll(topic.get(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(pollDurationInMinutes));
        return Optional.ofNullable(mapper.convertValue(pollRepository.save(poll), PollDto.class));
    }

    private Integer setDurationInMinutes(Integer durationInMinutes) {
        if (durationInMinutes == null || durationInMinutes <= 0) {
            return 1;
        } else {
            return durationInMinutes;
        }
    }

    private void checkIfPollNotFound(Optional<Poll> poll, Integer id) {
        if (!poll.isPresent()) {
            throw new PollNotFoundException(id);
        }
    }

    private void checkIfTopicAlreadyHasAnOpenedPoll(CreatePollDto createPoll) {
        Optional<Poll> poll = pollRepository.findByTopic_IdAndEndDateAfter(createPoll.getTopicId(), LocalDateTime.now());
        if (poll.isPresent()) {
            throw new TopicAlreadyHasAnOpenedPollException(poll.get().getTopic().getName());
        }
    }

}