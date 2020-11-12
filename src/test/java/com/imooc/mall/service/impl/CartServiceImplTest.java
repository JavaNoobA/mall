package com.imooc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.form.CartForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 21:21
 */
@Slf4j
public class CartServiceImplTest extends MallApplicationTests {
    @Autowired
    private CartServiceImpl cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    public void addCart() {
        CartForm cartForm = new CartForm();
        cartForm.setProductId(26);
        cartForm.setSelected(true);
        cartService.addCart(26, cartForm);
    }

    @Test
    public void list() {
        ResponseVo<CartVo> cartVo = cartService.list(26);
        log.info(gson.toJson(cartVo));
    }
}