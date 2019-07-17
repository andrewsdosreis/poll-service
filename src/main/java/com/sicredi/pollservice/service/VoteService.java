package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.exception.UserAlreadyHasVotedForPollException;
import com.sicredi.pollservice.exception.VoteOptionInvalidException;
import com.sicredi.pollservice.model.VoteOption;
import com.sicredi.pollservice.model.request.CreateVoteDto;
import com.sicredi.pollservice.model.response.VoteDto;
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

    public Optional<VoteDto> create(CreateVoteDto createVote) {
        Vote vote = new Vote.Builder()
                            .setVote(checkIfVoteOptionIsInvalid(createVote))
                            .setUser(userService.checkAndReturnaValidUserToVote(createVote.getUserCpf()).get())
                            .setPoll(pollService.checkAndReturnValidPollToVote(createVote.getPollId()).get())
                            .build();

        checkIfUserAlreadyHasVotedForPoll(vote.getUser(), vote.getPoll());

        return Optional.ofNullable(mapper.convertValue(voteRepository.save(vote), VoteDto.class));
    }

    public Integer countVotesByPoll(Integer pollId) {
        return voteRepository.countByPoll_Id(pollId);
    }

    public Integer countVotesByPollAndVoteOption(Integer pollId, VoteOption voteOption) {
        return voteRepository.countByPoll_IdAndVote(pollId, voteOption);
    }

    private void checkIfUserAlreadyHasVotedForPoll(User user, Poll poll) {
        Optional<Vote> vote = voteRepository.findByUserAndPoll(user, poll);
        if (vote.isPresent()) {
            throw new UserAlreadyHasVotedForPollException(user.getCpf(), poll.getTopic().getName());
        }
    }

    private VoteOption checkIfVoteOptionIsInvalid(CreateVoteDto createVote) {
        try {
            return VoteOption.valueOf(createVote.getVote());
        } catch (Exception e) {
            throw new VoteOptionInvalidException(createVote.getVote());
        }
    }
}