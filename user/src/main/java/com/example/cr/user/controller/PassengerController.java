package com.example.cr.user.controller;

import com.example.cr.common.response.R;
import com.example.cr.user.request.PassengerRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passenger")
public class PassengerController {
    @PostMapping("save")
    public R<Object> save(@Valid @RequestBody PassengerRequest request) {
        return R.ok();
    }
}
