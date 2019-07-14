package com.sicredi.pollservice.controller;

import com.sicredi.pollservice.model.UserDto;
import com.sicredi.pollservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/user")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return userService.findById(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}