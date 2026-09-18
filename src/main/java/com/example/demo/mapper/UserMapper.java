package com.example.demo.mapper;

import com.example.demo.entity.User;

public interface UserMapper {
    User findByUsername(String username);
    int insert(User user);
}
