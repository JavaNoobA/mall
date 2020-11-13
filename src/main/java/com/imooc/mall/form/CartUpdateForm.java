package com.imooc.mall.form;

import lombok.Data;

/**
 * @author pengfei.zhao
 * @date 2020/11/13 15:39
 */
@Data
public class CartUpdateForm {
    private Integer quantity;

    private Boolean selected = true;
}
