package com.imooc.mall.dao;

import com.imooc.mall.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pengfei.zhao
 * @date 2020/10/31 16:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
    }

    @Test
    public void queryById() {
        Category category = categoryMapper.selectByPrimaryKey(100001);
        System.out.println(category);
    }
}