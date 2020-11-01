package com.imooc.mall.enums;

import lombok.Getter;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 22:05
 */
@Getter
public enum RoleEnum {
    CUSTOMER(1, "普通用户"),
    ADMIN(0, "管理员"),
    ;

    Integer code;
    String desc;

    RoleEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
