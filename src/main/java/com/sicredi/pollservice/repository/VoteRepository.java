package com.sicredi.pollservice.repository;

import com.sicredi.pollservice.entity.Vote;

import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Integer> {

}