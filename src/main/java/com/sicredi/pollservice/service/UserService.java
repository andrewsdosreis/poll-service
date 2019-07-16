package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.exception.UserNotFoundException;
import com.sicredi.pollservice.model.response.UserDto;
import com.sicredi.pollservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ObjectMapper mapper;
    private UserRepository userRepository;

    @Autowired
    public UserService(ObjectMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public Optional<UserDto> find(Integer id) {
        return Optional.of(mapper.convertValue(findById(id), UserDto.class));
    }

    public Optional<User> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        checkIfUserNotFound(user, id);
        return user;
    }

    public Optional<User> findByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        checkIfUserNotFound(user, cpf);
        return user;
    }

    private void checkIfUserNotFound(Optional<User> user, String cpf) {
        if (!user.isPresent()) {
            throw new UserNotFoundException(cpf);
        }
    }

    private void checkIfUserNotFound(Optional<User> user, Integer id) {
        if (!user.isPresent()) {
            throw new UserNotFoundException(id);
        }
    }

}