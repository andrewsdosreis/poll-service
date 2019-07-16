package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyExistsException;
import com.sicredi.pollservice.exception.TopicNotFoundException;
import com.sicredi.pollservice.model.request.CreateTopicDto;
import com.sicredi.pollservice.model.response.TopicDto;
import com.sicredi.pollservice.repository.TopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private ObjectMapper mapper;
    private TopicRepository topicRepository;

    @Autowired
    public TopicService(ObjectMapper mapper, TopicRepository topicRepository) {
        this.mapper = mapper;
        this.topicRepository = topicRepository;
    }

    public Optional<TopicDto> find(Integer id) {
        return Optional.ofNullable(mapper.convertValue(findById(id), TopicDto.class));
    }

    public Optional<Topic> findById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);
        checkIfTopicNotFound(topic, id);
        return topic;
    }

    public Optional<TopicDto> create(CreateTopicDto newTopic) {
        checkIfTopicAlreadyExists(newTopic);
        Topic topic = mapper.convertValue(newTopic, Topic.class);
        return Optional.ofNullable(mapper.convertValue(topicRepository.save(topic), TopicDto.class));
    }

    private void checkIfTopicAlreadyExists(CreateTopicDto newTopic) {
        Optional<Topic> topic = topicRepository.findByName(newTopic.getName());

        if (topic.isPresent()) {
            throw new TopicAlreadyExistsException(newTopic.getName());
        }
    }

    private void checkIfTopicNotFound(Optional<Topic> topic, Integer id) {
        if (!topic.isPresent()) {
            throw new TopicNotFoundException(id);
        }
    }

}