package com.sicredi.pollservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.exception.PollIsClosedException;
import com.sicredi.pollservice.exception.PollIsOpenException;
import com.sicredi.pollservice.exception.PollNotFoundException;
import com.sicredi.pollservice.exception.TopicAlreadyHasAnOpenedPollException;
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

    public Optional<List<PollDto>> list() {
        return Optional.ofNullable(pollRepository
                                    .findAll()
                                    .stream()
                                    .map(poll -> mapper.convertValue(poll, PollDto.class))
                                    .collect(Collectors.toList()));
    }

    public Optional<PollDto> find(Integer id) {
        return Optional.ofNullable(mapper.convertValue(findById(id).get(), PollDto.class));
    }

    public Optional<PollDto> create(CreatePollDto createPoll) {
        checkIfTopicAlreadyHasAnOpenedPoll(createPoll);
        Poll poll = new Poll.Builder()
                            .setTopic(topicService.findById(createPoll.getTopicId()).get())
                            .setStartDate(LocalDateTime.now())
                            .setEndDate(LocalDateTime.now().plusMinutes(setDurationInMinutes(createPoll.getDurationInMinutes())))
                            .setClosed(false)
                            .build();
        
        return Optional.ofNullable(mapper.convertValue(save(poll), PollDto.class));
    }

    public List<Poll> listAllRecentlyClosedPolls() {
        return pollRepository.findAllByClosedAndEndDateBefore(false, LocalDateTime.now());
    }

    public Optional<Poll> checkAndReturnValidPollToVote(Integer id) {
        Optional<Poll> poll = findById(id);
        checkIfPollIsOpenToVote(poll.get());
        return poll;
    }

    public Optional<Poll> checkIfPollIsClosedToGetPollResults(Integer id) {
        Optional<Poll> poll = findById(id);
        checkIfPollIsClosedToGetPollResult(poll.get());
        return poll;
    }

    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    private Optional<Poll> findById(Integer id) {
        Optional<Poll> poll = pollRepository.findById(id);
        checkIfPollNotFound(poll, id);
        return poll;
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

    private void checkIfPollIsOpenToVote(Poll poll) {
        if (!poll.isOpen()) {
            throw new PollIsClosedException(poll.getTopic().getName());
        }
    }

    private void checkIfPollIsClosedToGetPollResult(Poll poll) {
        if (poll.isOpen()) {
            throw new PollIsOpenException(poll.getId(), poll.getTopic().getName());
        }
    }

    private void checkIfTopicAlreadyHasAnOpenedPoll(CreatePollDto createPoll) {
        Optional<Poll> poll = pollRepository.findByTopic_IdAndEndDateAfter(createPoll.getTopicId(), LocalDateTime.now());
        if (poll.isPresent()) {
            throw new TopicAlreadyHasAnOpenedPollException(poll.get().getTopic().getName());
        }
    }
}