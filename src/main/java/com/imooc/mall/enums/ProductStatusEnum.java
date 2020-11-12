package com.imooc.mall.enums;

import lombok.Getter;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 19:19
 */
@Getter
public enum ProductStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3);

    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
