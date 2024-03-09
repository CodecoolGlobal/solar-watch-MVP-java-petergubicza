package com.codecool.solarwatch.model.entity;

import com.codecool.solarwatch.model.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class appUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private Set<Role> roles;

    public appUser() {
    }

    public appUser(String name, String password, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void makeAdmin() {
        Set<Role> updatedRoles = new HashSet<>(roles);
        updatedRoles.add(Role.ROLE_ADMIN);
        setRoles(updatedRoles);
    }
}
