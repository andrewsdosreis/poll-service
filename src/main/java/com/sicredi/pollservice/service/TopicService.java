package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyExistsException;
import com.sicredi.pollservice.exception.TopicNotFoundException;
import com.sicredi.pollservice.model.request.CreateTopic;
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

    public Optional<Topic> findById(Integer id) {
        Optional<Topic> topic = topicRepository.findById(id);
        checkIfTopicNotFound(topic, id);
        return topic;
    }

    public Optional<TopicDto> create(CreateTopic newTopic) {
        checkIfTopicAlreadyExists(newTopic);

        return Optional.ofNullable(this.mapper.convertValue(newTopic, Topic.class))
                .flatMap(obj -> Optional.ofNullable(this.topicRepository.save(obj)))
                .map(obj -> this.mapper.convertValue(obj, TopicDto.class));
    }

    private void checkIfTopicAlreadyExists(CreateTopic newTopic) {
        Optional<Topic> topic = topicRepository.findByName(newTopic.getName());

        if (!topic.isPresent()) {
            throw new TopicAlreadyExistsException(newTopic.getName());
        }
    }

    private void checkIfTopicNotFound(Optional<Topic> topic, Integer id) {
        if (!topic.isPresent()) {
            throw new TopicNotFoundException(id);
        }
    }

}