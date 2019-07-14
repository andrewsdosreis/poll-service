package com.sicredi.pollservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.User;
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

    public Optional<UserDto> findById(Integer id) {
        return Optional.ofNullable(mapper.convertValue(userRepository.findById(id), UserDto.class));
    }

    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

}