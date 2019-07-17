package com.sicredi.pollservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.exception.UserInvalidException;
import com.sicredi.pollservice.exception.UserIsUnableToVoteException;
import com.sicredi.pollservice.exception.UserNotFoundException;
import com.sicredi.pollservice.model.UserInfoDto;
import com.sicredi.pollservice.model.response.UserDto;
import com.sicredi.pollservice.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    private ObjectMapper mapper;
    private UserRepository userRepository;
    private UserService userService;
    private UserInfoApiService userInfoApiService;

    @Before
    public void initTests() {
        mapper = Mockito.mock(ObjectMapper.class);
        userRepository = Mockito.mock(UserRepository.class);
        userInfoApiService = Mockito.mock(UserInfoApiService.class);
        this.userService = new UserService(mapper, userRepository, userInfoApiService);
    }

    @Test
    public void test_list_isValid() {
        User user = new User(1, "Andrews dos Reis", "01926679040");
        UserDto userDto = new UserDto(1, "Andrews dos Reis", "01926679040");
        
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        when(mapper.convertValue(user, UserDto.class)).thenReturn(userDto);

        Optional<List<UserDto>> result = userService.list();
        assertFalse(result.get().isEmpty());
    }

    @Test
    public void test_find_isValid() {
        User user = new User(1, "Andrews dos Reis", "01926679040");
        UserDto userDto = new UserDto(1, "Andrews dos Reis", "01926679040");

        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));
        when(mapper.convertValue(user, UserDto.class)).thenReturn(userDto);

        Optional<UserDto> result = userService.find(1);
        assertTrue(result.isPresent());
    }

    @Test
    public void test_find_NotFoundException() {
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));

        try {
            userService.find(1);
        } catch (UserNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_checkAndReturnaValidUserToVote_isValid() {
        User user = new User(1, "Andrews dos Reis", "01926679040");
        UserInfoDto userInfoDto = new UserInfoDto("ABLE_TO_VOTE");

        when(userRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(userInfoApiService.callUserInfoApi(Mockito.anyString())).thenReturn(userInfoDto);

        Optional<User> result = userService.checkAndReturnaValidUserToVote("01926679040");
        assertTrue(result.isPresent());
    }

    @Test
    public void test_checkAndReturnaValidUserToVote_UserIsUnableToVoteException() {
        User user = new User(1, "Andrews dos Reis", "01926679040");
        UserInfoDto userInfoDto = new UserInfoDto("UNABLE_TO_VOTE");

        when(userRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(userInfoApiService.callUserInfoApi(Mockito.anyString())).thenReturn(userInfoDto);

        try {
            userService.checkAndReturnaValidUserToVote("01926679040");
        } catch(UserIsUnableToVoteException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void test_checkAndReturnaValidUserToVote_UserInvalidException() {
        User user = new User(1, "Andrews dos Reis", "01926679040");

        when(userRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        when(userInfoApiService.callUserInfoApi(Mockito.anyString())).thenThrow(new UserInvalidException(""));

        try {
            userService.checkAndReturnaValidUserToVote("01926679040");
        } catch(UserInvalidException e) {
            assertNotNull(e);
        }
    }
}