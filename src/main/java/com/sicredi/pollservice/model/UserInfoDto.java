package com.sicredi.pollservice.model;

import java.io.Serializable;

public class UserInfoDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String status;

    public UserInfoDto() {
        super();
    }

    public UserInfoDto(String status) {
        super();
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}