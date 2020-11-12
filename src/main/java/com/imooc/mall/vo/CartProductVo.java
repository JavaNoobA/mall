package com.imooc.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 20:45
 */
@Data
@AllArgsConstructor
public class CartProductVo {
    private Integer productId;

    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Boolean productSelected;
}
