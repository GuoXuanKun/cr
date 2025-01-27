package com.example.cr.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号长度必须为11位")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
