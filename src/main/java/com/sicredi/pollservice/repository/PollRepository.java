package com.sicredi.pollservice.repository;

import java.util.Optional;

import com.sicredi.pollservice.entity.Poll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository<Poll, Integer> {

    Optional<Poll> findByTopic_Id(Integer topicId);
}