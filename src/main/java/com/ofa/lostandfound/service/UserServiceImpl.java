package com.ofa.lostandfound.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public String getUserName(String userId) {
        // Mock implementation
        return "User " + userId;
    }
}