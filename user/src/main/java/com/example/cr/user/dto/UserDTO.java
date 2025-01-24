package com.example.cr.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank
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
