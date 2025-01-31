package com.example.cr.user.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassengerRequest {
    @NotNull
    private Long userId;

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty
    private String idCard;

    @NotEmpty
    private String type;
}
