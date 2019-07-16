package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.exception.PollIsClosedException;
import com.sicredi.pollservice.exception.UserAlreadyHasVotedForPollException;
import com.sicredi.pollservice.model.VoteOption;
import com.sicredi.pollservice.model.request.CreateVoteDto;
import com.sicredi.pollservice.model.response.PollResultDto;
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

    public Optional<VoteDto> create(CreateVoteDto createPoll) {
        Optional<User> user = userService.findByCpf(createPoll.getUserCpf());
        Optional<Poll> poll = pollService.findById(createPoll.getPollId());
        checkIfPollIsOpenToVote(poll.get());
        checkIfUserAlreadyHasVotedForPoll(user.get(), poll.get());
        Vote vote = new Vote(poll.get(), user.get(), createPoll.getVote());
        return Optional.ofNullable(mapper.convertValue(voteRepository.save(vote), VoteDto.class));
    }

    public Optional<PollResultDto> getPollResult(Integer pollId) {
        Optional<Poll> poll = pollService.findById(pollId);
        Integer totalVotes = voteRepository.countByPoll_Id(poll.get().getId());
        Integer yesVotes = voteRepository.countByPoll_IdAndVote(poll.get().getId(), VoteOption.YES);
        Integer noVotes = voteRepository.countByPoll_IdAndVote(poll.get().getId(), VoteOption.NO);
        String result = setPollResult(yesVotes, noVotes);
        return Optional.ofNullable(new PollResultDto(totalVotes, yesVotes, noVotes, result));
    }

    private String setPollResult(Integer yesVotes, Integer noVotes) {
        if (yesVotes > noVotes) {
            return "YES";
        } else if (yesVotes < noVotes) {
            return "NO";
        } else {
            return "DRAW";
        }
    }

    private void checkIfPollIsOpenToVote(Poll poll) {
        if (!poll.isOpen()) {
            throw new PollIsClosedException(poll.getTopic().getName());
        }
    }

    private void checkIfUserAlreadyHasVotedForPoll(User user, Poll poll) {
        Optional<Vote> vote = voteRepository.findByUserAndPoll(user, poll);
        if (vote.isPresent()) {
            throw new UserAlreadyHasVotedForPollException(user.getCpf(), poll.getTopic().getName());
        }
    }

}