package com.sicredi.pollservice.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyHasAnOpenedPollException;
import com.sicredi.pollservice.exception.PollNotFoundException;
import com.sicredi.pollservice.model.request.CreatePollDto;
import com.sicredi.pollservice.model.response.PollDto;
import com.sicredi.pollservice.model.response.TopicDto;
import com.sicredi.pollservice.repository.PollRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PollServiceTests {

    private ObjectMapper mapper;
    private PollRepository pollRepository;
    private TopicService topicService;
    private PollService pollService;

    @Before
    public void initTests() {
        this.mapper = Mockito.mock(ObjectMapper.class);
        this.pollRepository = Mockito.mock(PollRepository.class);
        this.topicService = Mockito.mock(TopicService.class);
        this.pollService = new PollService(mapper, pollRepository, topicService);
    }

    @Test
    public void test_findById_isValid() {
        Poll poll = new Poll(1,
                new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil"),
                LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        when(pollRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(poll));
        Optional<Poll> result = pollService.findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    public void test_findById_NotFoundException() {
        when(pollRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        try {
            pollService.findById(1);
        } catch (PollNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_create_isValid() {
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        TopicDto topicDto = new TopicDto(1, "Você é a favor do desarmamento?",
                "Votação sobre o desarmamento no Brasil");
        Poll poll = new Poll(1, topic, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        PollDto pollDto = new PollDto(1, topicDto, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

        when(topicService.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(topic));
        when(pollRepository.findByTopic_IdAndEndDateAfter(Mockito.anyInt(), Mockito.any(LocalDateTime.class)))
                .thenReturn(Optional.ofNullable(null));
        when(pollRepository.save(Mockito.any(Poll.class))).thenReturn(poll);
        when(mapper.convertValue(poll, PollDto.class)).thenReturn(pollDto);

        Optional<PollDto> result = pollService.create(new CreatePollDto(1, 1));
        assertTrue(result.isPresent());
    }

    @Test
    public void test_create_TopicAlreadyHasAnOpenedPoll() {
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        Poll poll = new Poll(1, topic, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

        when(topicService.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(topic));
        when(pollRepository.findByTopic_IdAndEndDateAfter(Mockito.anyInt(), Mockito.any(LocalDateTime.class)))
                .thenReturn(Optional.ofNullable(poll));

        try {
            pollService.create(new CreatePollDto(1, 1));
        } catch (TopicAlreadyHasAnOpenedPollException e) {
            assertNotNull(e);
        }
    }
}