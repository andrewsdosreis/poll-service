package com.sicredi.pollservice.model.request;

import java.io.Serializable;

public class CreateTopicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public CreateTopicDto() {
        super();
    }

    public CreateTopicDto(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

}