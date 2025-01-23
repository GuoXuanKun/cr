package com.example.cr.user.dto;

public class UserDTO {
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
