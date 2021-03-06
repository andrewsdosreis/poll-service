package com.sicredi.pollservice.controller;

import java.util.List;

import com.sicredi.pollservice.model.request.CreatePollDto;
import com.sicredi.pollservice.model.response.PollDto;
import com.sicredi.pollservice.model.response.PollResultDto;
import com.sicredi.pollservice.service.PollResultService;
import com.sicredi.pollservice.service.PollService;

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
@RequestMapping(path = "/v1/poll")
@Api(value = "Polls")
public class PollController extends BaseController {

    private PollService pollService;
    private PollResultService pollResultService;

    @Autowired
    public PollController(PollService pollService, PollResultService pollResultService) {
        this.pollService = pollService;
        this.pollResultService = pollResultService;
    }

    @GetMapping()
    @ApiOperation(value = "List all Polls")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<PollDto>> list() {
        return pollService.list().map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a Poll by id")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<PollDto> find(@PathVariable Integer id) {
        return pollService.find(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @PostMapping()
    @ApiOperation(value = "Create a new Poll for a Topic")
    @ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<PollDto> create(@RequestBody CreatePollDto createPoll) {
        return pollService.create(createPoll).map(obj -> this.created(obj))
                .orElseGet(() -> this.expectationFailed(null));
    }

    @GetMapping(value = "/{id}/result")
    @ApiOperation(value = "Return the result of an closed Poll")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<PollResultDto> getPollResult(@PathVariable Integer id) {
        return pollResultService.getPollResult(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}