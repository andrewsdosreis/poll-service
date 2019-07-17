package com.sicredi.pollservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.messagebroker.producer.PollResultProducer;
import com.sicredi.pollservice.model.VoteOption;
import com.sicredi.pollservice.model.response.PollResultDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PollResultServiceTests {

    private VoteService voteService;
    private PollService pollService;
    private PollResultProducer pollResultProducer;
    private PollResultService pollResultService;

    @Before
    public void initTests() {
        this.voteService = Mockito.mock(VoteService.class);
        this.pollService = Mockito.mock(PollService.class);
        this.pollResultProducer = Mockito.mock(PollResultProducer.class);
        this.pollResultService = new PollResultService(voteService, pollService, pollResultProducer);
    }

    @Test
    public void test_getPollResult_isValid() {
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        Poll poll = new Poll(1, topic, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), false);

        when(pollService.checkIfPollIsClosedToGetPollResults(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));
        when(voteService.countVotesByPoll(Mockito.anyInt())).thenReturn(10);
        when(voteService.countVotesByPollAndVoteOption(Mockito.anyInt(), Mockito.any(VoteOption.class))).thenReturn(5);

        Optional<PollResultDto> result = pollResultService.getPollResult(1);

        assertTrue(result.isPresent());
        assertTrue(result.get().getYesVotes().equals(5));
        assertTrue(result.get().getNoVotes().equals(5));
        assertTrue(result.get().getTotalVotes().equals(10));
        assertEquals(result.get().getWinningOption(), "DRAW");
    }
}