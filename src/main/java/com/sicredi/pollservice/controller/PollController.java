package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.OpenPollDto;
import com.sicredi.pollservice.model.PollDto;
import com.sicredi.pollservice.model.PollResultDto;
import com.sicredi.pollservice.service.PollService;
import com.sicredi.pollservice.service.VoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/poll")
public class PollController extends BaseController {

    private PollService pollService;
    private VoteService voteService;

    @Autowired
    public PollController(PollService pollService, VoteService voteService) {
        this.pollService = pollService;
        this.voteService = voteService;
    }

    @PostMapping()
    public ResponseEntity<PollDto> openPoll(@RequestBody OpenPollDto openPoll) {
        return pollService.openPoll(openPoll).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @GetMapping(value = "/{topicId}")
    public ResponseEntity<PollResultDto> getPollResult(@PathVariable Integer topicId) {
        return voteService.getPollResult(topicId).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}