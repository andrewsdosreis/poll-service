package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.TopicDto;
import com.sicredi.pollservice.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/topic")
public class TopicController extends BaseController {

    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping()
    public ResponseEntity<TopicDto> insert(@RequestBody TopicDto newTopic) {
        return topicService.create(newTopic).map(obj -> this.created(obj))
                .orElseGet(() -> this.expectationFailed(null));
    }

}