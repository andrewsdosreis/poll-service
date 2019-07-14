package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.OpenPollDto;
import com.sicredi.pollservice.model.PollDto;
import com.sicredi.pollservice.service.PollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/poll")
public class PollController extends BaseController {

    private PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping()
    public ResponseEntity<PollDto> openPoll(@RequestBody OpenPollDto openPoll) {
        return pollService.openPoll(openPoll).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}