package com.imooc.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 21:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;
}
