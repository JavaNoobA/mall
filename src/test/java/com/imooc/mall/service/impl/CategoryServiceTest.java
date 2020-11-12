package com.imooc.mall.service.impl;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 17:31
 */
@Slf4j
public class CategoryServiceTest extends MallApplicationTests {
    @Autowired
    private ICategoryService categoryService;

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100005, set);

        log.info("category Id = {}", set);
    }
}