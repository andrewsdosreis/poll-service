package com.sicredi.pollservice.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyExistsException;
import com.sicredi.pollservice.exception.TopicNotFoundException;
import com.sicredi.pollservice.model.request.CreateTopic;
import com.sicredi.pollservice.model.response.TopicDto;
import com.sicredi.pollservice.repository.TopicRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTests {

    private ObjectMapper mapper;
    private TopicRepository topicRepository;
    private TopicService topicService;

    @Before
    public void initTests() {
        mapper = Mockito.mock(ObjectMapper.class);
        topicRepository = Mockito.mock(TopicRepository.class);
        topicService = new TopicService(mapper, topicRepository);
    }

    @Test
    public void test_findById_isValid() {
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        when(topicRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(topic));
        Optional<Topic> result = topicRepository.findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    public void test_findById_NotFoundException() {
        when(topicRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));

        try {
            topicService.findById(1);
        } catch(TopicNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_create_isValid() {
        CreateTopic newTopic = new CreateTopic("Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        TopicDto topicDto = new TopicDto(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        
        when(mapper.convertValue(newTopic, Topic.class)).thenReturn(topic);
        when(mapper.convertValue(topic, TopicDto.class)).thenReturn(topicDto);
        when(topicRepository.save(Mockito.any(Topic.class))).thenReturn(topic);

        Optional<TopicDto> result = topicService.create(newTopic);
        
        assertTrue(result.isPresent());
    }

    @Test
    public void test_create_AlreadyExistsException() {
        CreateTopic newTopic = new CreateTopic("Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        Topic topic = new Topic(1, "Você é a favor do desarmamento?", "Votação sobre o desarmamento no Brasil");
        
        when(mapper.convertValue(newTopic, Topic.class)).thenReturn(topic);
        when(topicRepository.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(topic));

        try {
            topicService.create(newTopic);
        } catch(TopicAlreadyExistsException e) {
            assertNotNull(e);
        }
    }
}