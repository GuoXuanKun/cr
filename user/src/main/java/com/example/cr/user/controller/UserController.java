package com.example.cr.user.controller;

import com.example.cr.common.response.R;
import com.example.cr.user.dto.UserDTO;
import com.example.cr.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户数量
     * @return
     */
    @GetMapping("/count")
    public R<Long> count() {
        long count = userService.count();
        return R.ok(count);
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public R<Integer> register(@Valid UserDTO userDTO) {
        int result = userService.register(userDTO);
//        return new Result<>(200, "OK", result);
        return R.ok(result);
    }

}
