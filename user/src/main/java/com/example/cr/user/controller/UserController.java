package com.example.cr.user.controller;

import com.example.cr.common.response.LoginResponse;
import com.example.cr.common.response.R;
import com.example.cr.common.util.CustomJWTUtils;
import com.example.cr.user.request.LoginRequest;
import com.example.cr.user.request.SendCodeRequest;
import com.example.cr.user.request.UserRequest;
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
     * @param request
     * @return
     */
    @PostMapping("/register")
    public R<Integer> register(@Valid @RequestBody UserRequest request) {
        int result = userService.register(request);
//        return new Result<>(200, "OK", result);
        return R.ok(result);
    }

    @PostMapping("/send-code")
    public R<String> sendCode(@Valid @RequestBody SendCodeRequest request) {
        userService.sendCode(request);
        return R.ok();
    }

    @PostMapping("/login")
    public R<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse user = userService.login(request);
        user.setToken(CustomJWTUtils.createToken(user.getId(), user.getMobile()));
        return R.ok(user);
    }

}
