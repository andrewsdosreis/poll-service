package com.sicredi.pollservice.service;

import java.util.List;
import java.util.Optional;

import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.messagebroker.producer.PollResultProducer;
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

    @Scheduled(fixedRate = 60000)
    private void closePoll() {
        logger.info("Begin to process the closed Polls");
        List<Poll> pollsToClose = pollService.findRecentClosedPolls();
        for (Poll poll : pollsToClose) {
            Optional<PollResultDto> pollResult = voteService.getPollResult(poll.getId());
            logger.info("Sending Poll result %s to message broker", pollResult.get().toString());
            pollResultProducer.produce(pollResult.get());
            poll.setClosed(true);
            pollService.save(poll);
            logger.info("Poll %s has been closed", poll.getId());
        }
    }
}