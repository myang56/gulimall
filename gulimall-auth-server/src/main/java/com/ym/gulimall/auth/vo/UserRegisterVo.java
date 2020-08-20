package com.ym.gulimall.auth.vo;

import lombok.Data;
import org.checkerframework.checker.units.qual.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterVo {

    @NotEmpty(message = "用户名不能为空")
//    @Length(min = 6, max = 19, message="用户名长度在6-18字符")
    private String username;

    @NotEmpty(message = "密码不能为空")
//    @Length(min = 6, max = 19, message="密码长度在6-18字符")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;
}
