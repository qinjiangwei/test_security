package com.example.test_security.service;

import com.example.test_security.pojo.User;

public interface UserService {
    User loadUserByUsername(String s);
}
