package com.sicredi.pollservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    protected <T> ResponseEntity<T> created(T object) {
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    protected <T> ResponseEntity<T> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected <T> ResponseEntity<T> expectationFailed(T object) {
        return new ResponseEntity<>(object, HttpStatus.EXPECTATION_FAILED);
    }
}