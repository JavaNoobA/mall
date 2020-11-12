package com.imooc.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 18:12
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
