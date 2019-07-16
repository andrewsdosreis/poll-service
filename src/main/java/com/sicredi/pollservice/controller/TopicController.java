package com.sicredi.pollservice.controller;

import java.util.List;

import com.sicredi.pollservice.model.request.CreateTopicDto;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/v1/topic")
@Api(value = "Topics")
public class TopicController extends BaseController {

    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping()
    @ApiOperation(value = "List all Topics")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<TopicDto>> list() {
        return topicService.list().map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a Topic by id")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<TopicDto> findById(@PathVariable Integer id) {
        return topicService.find(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @PostMapping()
    @ApiOperation(value = "Create a new Topic")
    @ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<TopicDto> create(@RequestBody CreateTopicDto createTopic) {
        return topicService.create(createTopic).map(obj -> this.created(obj))
                .orElseGet(() -> this.expectationFailed(null));
    }

}