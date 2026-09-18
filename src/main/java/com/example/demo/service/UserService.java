package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    String register(User user);   // 返回 null=成功，否则是错误信息
    String login(String username, String password);  // 返回 null=失败，否则是 token
}
