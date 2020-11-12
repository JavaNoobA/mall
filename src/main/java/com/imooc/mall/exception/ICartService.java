package com.imooc.mall.exception;

import com.imooc.mall.form.CartForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 21:10
 */
public interface ICartService {
    ResponseVo<CartVo> addCart(Integer uid, CartForm form);

    ResponseVo<CartVo> list(Integer uid);
}
