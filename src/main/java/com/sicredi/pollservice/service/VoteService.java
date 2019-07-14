package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.model.PollVote;
import com.sicredi.pollservice.model.VoteDto;
import com.sicredi.pollservice.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private ObjectMapper mapper;
    private VoteRepository voteRepository;
    private PollService pollService;
    private UserService userService;

    @Autowired
    public VoteService(ObjectMapper mapper, VoteRepository voteRepository, PollService pollService,
            UserService userService) {
        this.mapper = mapper;
        this.voteRepository = voteRepository;
        this.pollService = pollService;
        this.userService = userService;
    }

    public Optional<VoteDto> findById(Integer id) {
        return Optional.ofNullable(mapper.convertValue(voteRepository.findById(id), VoteDto.class));
    }

    public Optional<VoteDto> create(PollVote pollVote) {
        Optional<User> user = userService.findByCpf(pollVote.getUserCpf());
        Optional<Poll> poll = pollService.findByTopic(pollVote.getTopicId());

        Vote vote = new Vote(poll.get(), user.get(), pollVote.getVote());

        return Optional.ofNullable(mapper.convertValue(voteRepository.save(vote), VoteDto.class));
    }

}