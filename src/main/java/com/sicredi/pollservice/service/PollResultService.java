package com.sicredi.pollservice.service;

import java.util.List;
import java.util.Optional;

import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.messagebroker.producer.PollResultProducer;
import com.sicredi.pollservice.model.VoteOption;
import com.sicredi.pollservice.model.response.PollResultDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PollResultService {

    @Log
    private Logger logger;

    private VoteService voteService;
    private PollService pollService;
    private PollResultProducer pollResultProducer;

    @Autowired
    public PollResultService(VoteService voteService, PollService pollService, PollResultProducer pollResultProducer) {
        this.voteService = voteService;
        this.pollService = pollService;
        this.pollResultProducer = pollResultProducer;
    }

    public Optional<PollResultDto> getPollResult(Integer pollId) {
        Optional<Poll> poll = pollService.checkIfPollIsClosedToGetPollResults(pollId);
        PollResultDto pollResult = new PollResultDto.Builder()
                                                    .setPollId(pollId)
                                                    .setTopicName(poll.get().getTopicName())
                                                    .setStartDate(poll.get().getStartDate())
                                                    .setEndDate(poll.get().getEndDate())
                                                    .setTotalVotes(voteService.countVotesByPoll(pollId))
                                                    .setYesVotes(voteService.countVotesByPollAndVoteOption(pollId, VoteOption.YES))
                                                    .setNoVotes(voteService.countVotesByPollAndVoteOption(pollId, VoteOption.NO))
                                                    .setWinningOption()
                                                    .build();

        return Optional.ofNullable(pollResult);
    }

    @Scheduled(fixedRate = 30000)
    private void sendPollResultToMessageBroker() {
        logger.info("Begin to process the closed Polls");
        List<Poll> pollsToClose = pollService.listAllRecentlyClosedPolls();
        for (Poll poll : pollsToClose) {
            Optional<PollResultDto> pollResult = getPollResult(poll.getId());
            logger.info("Sending Poll result %s to message broker", pollResult.get().toString());
            pollResultProducer.produce(pollResult.get());
            poll.setClosed(true);
            pollService.save(poll);
            logger.info("Poll %s has been closed", poll.getId());
        }
    }
}