package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.exception.TopicAlreadyExistsException;
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
        return topicRepository.findById(id);
    }

    public Optional<TopicDto> create(CreateTopic newTopic) {
        if (checkIfTopicAlreadyExists(newTopic)) {
            throw new TopicAlreadyExistsException(newTopic.getName());
        }

        return Optional.ofNullable(this.mapper.convertValue(newTopic, Topic.class))
                .flatMap(obj -> Optional.ofNullable(this.topicRepository.save(obj)))
                .map(obj -> this.mapper.convertValue(obj, TopicDto.class));
    }

    private boolean checkIfTopicAlreadyExists(CreateTopic newTopic) {
        Optional<Topic> topic = topicRepository.findByName(newTopic.getName());
        return topic.isPresent();
    }

}