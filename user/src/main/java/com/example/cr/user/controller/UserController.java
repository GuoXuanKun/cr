package com.example.cr.user.controller;

import com.example.cr.common.response.Result;
import com.example.cr.user.dto.UserDTO;
import com.example.cr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户数量
     * @return
     */
    @GetMapping("/count")
    public Result<Long> count() {
        long count = userService.count();
        return Result.ok(count);
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public Result<Integer> register(UserDTO userDTO) {
        int result = userService.register(userDTO);
//        return new Result<>(200, "OK", result);
        return Result.ok(result);
    }

}
