package com.sicredi.pollservice.repository;

import com.sicredi.pollservice.entity.Poll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository<Poll, Integer> {

}