package com.sicredi.pollservice.repository;

import java.util.List;
import java.util.Optional;

import com.sicredi.pollservice.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByCpf(String cpf);

    List<User> findAll();
}