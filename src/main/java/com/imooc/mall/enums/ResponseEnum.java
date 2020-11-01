package com.imooc.mall.enums;

import lombok.Getter;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 21:35
 */
@Getter
public enum ResponseEnum {
    ERROR(-1, "服务端异常"),
    SUCCESS(0, "成功"),
    USER_EXIST(1, "用户名已存在"),
    EMAIL_EXIST(2, "邮箱已存在"),
    PARAM_ERROR(3, "参数检验错误"),
    PASSWORD_ERROR(4, "密码错误"),
    NEED_LOGIN(10, "用户未登录,无法获取当前用户信息"),
    ;

    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
