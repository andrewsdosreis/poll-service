package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.entity.Topic;
import com.sicredi.pollservice.model.request.CreateTopic;
import com.sicredi.pollservice.model.response.TopicDto;
import com.sicredi.pollservice.service.TopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<Topic> findById(@PathVariable Integer id) {
        return topicService.findById(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @PostMapping()
    public ResponseEntity<TopicDto> insert(@RequestBody CreateTopic newTopic) {
        return topicService.create(newTopic).map(obj -> this.created(obj))
                .orElseGet(() -> this.expectationFailed(null));
    }

}