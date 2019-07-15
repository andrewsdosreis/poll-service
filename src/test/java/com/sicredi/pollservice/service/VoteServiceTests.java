package com.sicredi.pollservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.exception.PollIsClosedException;
import com.sicredi.pollservice.exception.UserAlreadyHasVotedForPollException;
import com.sicredi.pollservice.model.VoteOption;
import com.sicredi.pollservice.model.request.PollVote;
import com.sicredi.pollservice.model.response.PollDto;
import com.sicredi.pollservice.model.response.PollResultDto;
import com.sicredi.pollservice.model.response.UserDto;
import com.sicredi.pollservice.model.response.VoteDto;
import com.sicredi.pollservice.repository.VoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTests {

    private ObjectMapper mapper;
    private VoteRepository voteRepository;
    private PollService pollService;
    private UserService userService;
    private VoteService voteService;

    @Before
    public void initTests() {
        this.mapper = Mockito.mock(ObjectMapper.class);
        this.voteRepository = Mockito.mock(VoteRepository.class);
        this.pollService = Mockito.mock(PollService.class);
        this.userService = Mockito.mock(UserService.class);
        this.voteService = new VoteService(mapper, voteRepository, pollService, userService);
    }

    @Test
    public void test_create_isValid() {
        PollVote pollVote = new PollVote(1, "01926679040", VoteOption.YES);
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        User user = new User(1, "Andrews dos Reis", "01926679040");
        Poll poll = new Poll(1, topic, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        Vote vote = new Vote(1, poll, user, VoteOption.YES);
        PollDto pollDto = mapper.convertValue(poll, PollDto.class);
        UserDto userDto = mapper.convertValue(user, UserDto.class);
        VoteDto voteDto = new VoteDto(1, pollDto, userDto, VoteOption.YES);

        when(userService.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(pollService.findByTopic(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));
        when(voteRepository.findByUserAndPoll(user, poll)).thenReturn(Optional.ofNullable(null));
        when(voteRepository.save(Mockito.any(Vote.class))).thenReturn(vote);
        when(mapper.convertValue(vote, VoteDto.class)).thenReturn(voteDto);

        Optional<VoteDto> result = voteService.create(pollVote);
        assertTrue(result.isPresent());
    }

    @Test
    public void test_create_PollIsClosedToVoteException() {
        PollVote pollVote = new PollVote(1, "01926679040", VoteOption.YES);
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        User user = new User(1, "Andrews dos Reis", "01926679040");
        Poll poll = new Poll(1, topic, LocalDateTime.now().minusMinutes(10), LocalDateTime.now());

        when(userService.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(pollService.findByTopic(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));

        try {
            voteService.create(pollVote);
        } catch (PollIsClosedException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_create_UserAlreadyHasVotedForPoll() {
        PollVote pollVote = new PollVote(1, "01926679040", VoteOption.YES);
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        User user = new User(1, "Andrews dos Reis", "01926679040");
        Poll poll = new Poll(1, topic, LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(1));
        Vote vote = new Vote(1, poll, user, VoteOption.YES);

        when(userService.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(pollService.findByTopic(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));
        when(voteRepository.findByUserAndPoll(user, poll)).thenReturn(Optional.ofNullable(vote));

        try {
            voteService.create(pollVote);
        } catch (UserAlreadyHasVotedForPollException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_getPollResult_isValid() {
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        Poll poll = new Poll(1, topic, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

        when(pollService.findByTopic(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));
        when(voteRepository.countByPoll_Id(Mockito.anyInt())).thenReturn(10);
        when(voteRepository.countByPoll_IdAndVote(Mockito.anyInt(), Mockito.any(VoteOption.class))).thenReturn(5);

        Optional<PollResultDto> result = voteService.getPollResult(1);
        assertTrue(result.isPresent());
        assertTrue(result.get().getYesVotes().equals(5));
        assertTrue(result.get().getNoVotes().equals(5));
        assertTrue(result.get().getTotalVotes().equals(10));
        assertEquals(result.get().getResult(), "DRAW");        
    }
}