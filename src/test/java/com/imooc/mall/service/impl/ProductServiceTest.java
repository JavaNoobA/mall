package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 18:28
 */
public class ProductServiceTest extends MallApplicationTests {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void testListProduct() {
        productService.list(100002, 1, 1);
    }

}