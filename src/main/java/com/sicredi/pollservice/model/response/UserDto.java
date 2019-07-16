package com.sicredi.pollservice.model.response;

import java.io.Serializable;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String cpf;

    public UserDto() {
        super();
    }

    public UserDto(Integer id, String name, String cpf) {
        super();
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", cpf=" + cpf + ", name=" + name + "]";
    }

}