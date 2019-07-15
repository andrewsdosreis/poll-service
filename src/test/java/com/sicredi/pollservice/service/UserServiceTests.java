package com.sicredi.pollservice.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.pollservice.entity.User;
import com.sicredi.pollservice.exception.UserNotFoundException;
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

    @Before
    public void initTests() {
        mapper = Mockito.mock(ObjectMapper.class);
        userRepository = Mockito.mock(UserRepository.class);
        this.userService = new UserService(mapper, userRepository);
    }

    @Test
    public void test_findByCpf_isValid() {
        User user = new User(1, "Andrews dos Reis", "01926679040");
        when(userRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Optional<User> result = userService.findByCpf("01926679040");
        assertTrue(result.isPresent());
    }

    @Test
    public void test_findByCpf_NotFoundException() {
        when(userRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        try {
            userService.findByCpf("01926679040");
        } catch (UserNotFoundException e) {
            assertNotNull(e);
        }
    }
}