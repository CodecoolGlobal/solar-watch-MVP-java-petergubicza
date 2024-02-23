package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.Role;
import com.codecool.solarwatch.model.entity.User;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));

    }

    public void addRoleFor(User user, Role role) {

        Set<Role> oldRoles = user.getRoles();

        Set<Role> copiedRoles = new HashSet<>(oldRoles);
        copiedRoles.add(role);

        userRepository.updateUser(new User(user.getName(), user.getPassword(), Set.copyOf(copiedRoles)));

    }
}
