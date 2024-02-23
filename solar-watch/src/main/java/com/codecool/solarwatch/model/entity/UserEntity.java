package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;

    public UserEntity() {
    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
