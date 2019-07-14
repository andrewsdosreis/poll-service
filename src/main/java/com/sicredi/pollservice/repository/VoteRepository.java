package com.sicredi.pollservice.repository;

import com.sicredi.pollservice.entity.Vote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer> {

}