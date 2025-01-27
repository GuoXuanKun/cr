package com.example.cr.user.controller;

import com.example.cr.common.response.R;
import com.example.cr.user.request.LoginDTO;
import com.example.cr.user.request.SendCodeDTO;
import com.example.cr.user.request.UserDTO;
import com.example.cr.user.response.LoginResponse;
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
    public R<Integer> register(@Valid @RequestBody UserDTO userDTO) {
        int result = userService.register(userDTO);
//        return new Result<>(200, "OK", result);
        return R.ok(result);
    }

    @PostMapping("/send-code")
    public R<String> sendCode(@Valid @RequestBody SendCodeDTO dto) {
        userService.sendCode(dto);
        return R.ok();
    }

    @PostMapping("/login")
    public R<LoginResponse> login(@Valid @RequestBody LoginDTO dto) {
        LoginResponse user = userService.login(dto);
        return R.ok(user);
    }

}
