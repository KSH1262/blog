package com.example.test2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinDto {

    @NotBlank(message = "필수 입력 값입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "필수 입력 값입니다.")
    @Size(min = 5, max = 30, message = "비밀번호는 5자 이상 30자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "필수 입력 값입니다.")
    @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;
}
