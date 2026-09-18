// service/UserServiceImpl.java（实现——重点看业务逻辑）
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 内存里的"已登录名单"：token → 用户名（真实项目用 Redis，第5天学）
    private Map<String, String> tokenMap = new HashMap<>();

    @Override
    public String register(User user) {
        // 业务逻辑1：查重
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return "用户名已存在";
        }
        userMapper.insert(user);
        return null;
    }

    @Override
    public String login(String username, String password) {
        // 业务逻辑2：查用户 + 比对密码
        User user = userMapper.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;  // 用户不存在 或 密码错，统称"登录失败"
        }
        // 业务逻辑3：发 token
        String token = UUID.randomUUID().toString();
        tokenMap.put(token, username);
        return token;
    }
}
