package com.imooc.mall.service;

import com.imooc.mall.form.UserForm;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 17:01
 */
public interface IUserService {
    /**
     * 注册
     */
    ResponseVo register(UserForm userForm);

    /**
     * 登录
     */
}
