package com.sicredi.pollservice.repository;

import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.model.VoteEnum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer> {

    Integer countByPoll_Id(Integer pollId);
    Integer countByPoll_IdAndVote(Integer pollId, VoteEnum vote);
}