package com.codecool.solarwatch.model.payload;

import com.codecool.solarwatch.model.Role;

import java.util.Set;

public class UserRequest {
    private String name;
    private String password;
    private boolean isAdmin;

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

