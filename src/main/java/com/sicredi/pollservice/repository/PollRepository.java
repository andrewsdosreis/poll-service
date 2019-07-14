package com.sicredi.pollservice.repository;

import com.sicredi.pollservice.entity.Poll;

import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Integer> {

}