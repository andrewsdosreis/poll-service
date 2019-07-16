package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.request.CreateVoteDto;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/v1/vote")
@Api(value = "Votes")
public class VoteController extends BaseController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a Vote by id")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<VoteDto> findById(@PathVariable Integer id) {
        return voteService.find(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @PostMapping()
    @ApiOperation(value = "Register an User's Vote for a Poll")
    @ApiResponses({ @ApiResponse(code = 201, message = "Created"), @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<VoteDto> create(@RequestBody CreateVoteDto pollVote) {
        return voteService.create(pollVote).map(obj -> this.created(obj)).orElseGet(() -> this.noContent());
    }

}