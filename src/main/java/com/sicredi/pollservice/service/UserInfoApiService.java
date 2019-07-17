package com.sicredi.pollservice.service;

import com.sicredi.pollservice.exception.UserInvalidException;
import com.sicredi.pollservice.model.UserInfoDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserInfoApiService {

    @Value("${user-info.api}")
    private String userInfoApiURI;

    public UserInfoDto callUserInfoApi(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(userInfoApiURI + cpf, UserInfoDto.class);
        } catch (Exception e) {
            throw new UserInvalidException(cpf);
        }
    }
    
}