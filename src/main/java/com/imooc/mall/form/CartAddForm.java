package com.imooc.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pengfei.zhao
 * @date 2020/11/13 16:30
 */
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
