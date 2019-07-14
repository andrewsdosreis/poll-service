package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.request.PollVote;
import com.sicredi.pollservice.model.response.VoteDto;
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
@RequestMapping(path = "/v1/vote")
public class VoteController extends BaseController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VoteDto> findById(@PathVariable Integer id) {
        return voteService.findById(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @PostMapping()
    public ResponseEntity<VoteDto> create(@RequestBody PollVote pollVote) {
        return voteService.create(pollVote).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}