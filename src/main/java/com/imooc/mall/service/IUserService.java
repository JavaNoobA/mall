package com.imooc.mall.service;

import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 17:01
 */
public interface IUserService {
    /**
     * 注册
     */
    ResponseVo<User> register(UserRegisterForm userRegisterForm);

    /**
     * 登录
     */
    ResponseVo<User> login(String username, String password);
}
