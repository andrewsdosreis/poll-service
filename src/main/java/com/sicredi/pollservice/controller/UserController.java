package com.sicredi.pollservice.controller;

import java.util.List;

import com.sicredi.pollservice.model.response.UserDto;
import com.sicredi.pollservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/v1/user")
@Api(value = "Users")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ApiOperation(value = "List all Users")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<List<UserDto>> list() {
        return userService.list().map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a User by id")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return userService.find(id).map(obj -> this.ok(obj)).orElseGet(() -> this.noContent());
    }

}