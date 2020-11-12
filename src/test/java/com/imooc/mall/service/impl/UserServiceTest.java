package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.form.UserRegisterForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pengfei.zhao
 * @date 2020/11/1 21:09
 */
@Transactional
public class UserServiceTest extends MallApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void register() {
        UserRegisterForm userRegisterForm = new UserRegisterForm();
        userRegisterForm.setUsername("test");
        userRegisterForm.setPassword("test");
        final ResponseVo<User> responseVo = userService.register(userRegisterForm);
        Assert.assertTrue(responseVo.getStatus() == 0);
    }

    @Test
    public void login() {
        String username = "erudev";
        String password = "123456";
        final ResponseVo<User> responseVo = userService.login(username, password);
        Assert.assertTrue(responseVo.getStatus() == 0);
    }
}