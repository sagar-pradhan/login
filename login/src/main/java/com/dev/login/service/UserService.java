package com.dev.login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.login.model.User;
import com.dev.login.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(u));
        return users;
    }
}
