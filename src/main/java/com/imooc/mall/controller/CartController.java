package com.imooc.mall.controller;

import com.imooc.mall.form.CartForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 20:48
 */
@RestController
public class CartController {

    @PostMapping("/carts")
    public ResponseVo<CartVo> addCart(@Valid @RequestBody CartForm cartForm) {
        return ResponseVo.success();
    }
}
