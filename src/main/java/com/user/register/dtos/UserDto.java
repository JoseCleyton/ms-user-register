package com.user.register.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank
    private String name;
    @NotBlank
    private String age;
    @NotBlank
    @Email
    private String email;
}
