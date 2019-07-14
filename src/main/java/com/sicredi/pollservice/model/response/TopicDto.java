package com.sicredi.pollservice.model.response;

import java.io.Serializable;

public class TopicDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;

    public TopicDto() {
        super();
    }

    public TopicDto(Integer id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}