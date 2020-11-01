package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTests;
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
    private UserService userService;

    @Test
    public void register() {
    }
}