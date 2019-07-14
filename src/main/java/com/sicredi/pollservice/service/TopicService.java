package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.model.TopicDto;
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

    public Optional<TopicDto> create(TopicDto newTopic) {
        return Optional.ofNullable(this.mapper.convertValue(newTopic, Topic.class))
                .flatMap(obj -> Optional.ofNullable(this.topicRepository.save(obj)))
                .map(obj -> this.mapper.convertValue(obj, TopicDto.class));
    }

}