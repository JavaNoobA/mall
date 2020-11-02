package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author pengfei.zhao
 * @date 2020/11/2 20:09
 */
@Data
public class UserLoginForm {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
