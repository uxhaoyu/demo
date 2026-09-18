// controller/UserController.java
package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/register")
    public Result<Void> register(@RequestBody User user) {
        String error = userService.register(user);
        if (error != null) {
            return Result.error(error);
        }
        return Result.<Void>success(null);
    }

    @PostMapping("/api/user/login")
    public Result<String> login(@RequestBody User user) {
        String token = userService.login(user.getUsername(), user.getPassword());
        if (token == null) {
            return Result.error("用户名或密码错误");
        }
        return Result.success(token);
    }
}
