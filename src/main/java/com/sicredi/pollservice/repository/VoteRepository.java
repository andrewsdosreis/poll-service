package com.sicredi.pollservice.repository;

import java.util.Optional;

import com.sicredi.pollservice.entity.Poll;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.entity.Vote;
import com.sicredi.pollservice.model.VoteOption;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer> {

    Optional<Vote> findByUserAndPoll(User user, Poll poll);
    Integer countByPoll_Id(Integer pollId);
    Integer countByPoll_IdAndVote(Integer pollId, VoteOption vote);
}