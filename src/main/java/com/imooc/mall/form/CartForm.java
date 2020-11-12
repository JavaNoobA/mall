package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 20:49
 */
@Data
public class CartForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
