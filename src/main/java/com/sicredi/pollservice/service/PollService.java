package com.sicredi.pollservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.PollAlreadyExistsException;
import com.sicredi.pollservice.exception.PollNotFoundException;
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
        Optional<Poll> poll = pollRepository.findByTopic_Id(topicId);
        checkIfPollNotFound(poll, topicId);
        return poll;
    }

    public Optional<PollDto> openPoll(OpenPollDto openPoll) {
        Optional<Topic> topic = topicService.findById(openPoll.getTopicId());

        checkIfPollAlreadyExists(openPoll);

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

    private void checkIfPollAlreadyExists(OpenPollDto openPoll) {
        Optional<Poll> poll = pollRepository.findByTopic_Id(openPoll.getTopicId());
        if (poll.isPresent()) {
            throw new PollAlreadyExistsException(poll.get().getTopic().getName());
        }
    }

    private void checkIfPollNotFound(Optional<Poll> poll, Integer topicId) {
        if (!poll.isPresent()) {
            throw new PollNotFoundException(topicId);
        }
    }

}