package com.sicredi.pollservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.exception.UserIsUnableToVoteException;
import com.sicredi.pollservice.exception.UserNotFoundException;
import com.sicredi.pollservice.model.UserInfoDto;
import com.sicredi.pollservice.model.response.UserDto;
import com.sicredi.pollservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ObjectMapper mapper;
    private UserRepository userRepository;
    private UserInfoApiService userInfoApiService;

    @Autowired
    public UserService(ObjectMapper mapper, UserRepository userRepository, UserInfoApiService userInfoApiService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userInfoApiService = userInfoApiService;
    }

    public Optional<List<UserDto>> list() {
        return Optional.ofNullable(userRepository
                                    .findAll()
                                    .stream()
                                    .map(user -> mapper.convertValue(user, UserDto.class))
                                    .collect(Collectors.toList()));
    }

    public Optional<UserDto> find(Integer id) {
        return Optional.ofNullable(mapper.convertValue(findById(id).get(), UserDto.class));
    }

    public Optional<User> checkAndReturnaValidUserToVote(String cpf) {
        Optional<User> user = findByCpf(cpf);
        checkIfUserIsValidAndAbleToVote(cpf);
        return user;
    }

    private Optional<User> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        checkIfUserNotFound(user, id);
        return user;
    }

    private Optional<User> findByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        checkIfUserNotFound(user, cpf);
        return user;
    }

    private void checkIfUserIsValidAndAbleToVote(String cpf) {
        UserInfoDto userInfo = callUserInfoApi(cpf);

        if (userInfo.getStatus().equals("UNABLE_TO_VOTE")) {
            throw new UserIsUnableToVoteException(cpf);
        }
    }

    private UserInfoDto callUserInfoApi(String cpf) {
        return userInfoApiService.callUserInfoApi(cpf);
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