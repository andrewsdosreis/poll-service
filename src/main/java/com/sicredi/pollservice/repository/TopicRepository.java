package com.sicredi.pollservice.repository;

import java.util.List;
import java.util.Optional;

import com.sicredi.pollservice.entity.Topic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Optional<Topic> findByName(String name);

    List<Topic> findAll();
}