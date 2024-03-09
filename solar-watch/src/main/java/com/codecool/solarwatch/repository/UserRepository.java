package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.appUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<appUser, Long> {
    Optional<appUser> findByName(String name);
}

