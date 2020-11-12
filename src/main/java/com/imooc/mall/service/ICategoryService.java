package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @author pengfei.zhao
 * @date 2020/11/2 21:22
 */
public interface ICategoryService {
    /**
     * 查询所有类目信息
     * @return 类目信息
     */
    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer categoryId, Set<Integer> categoryIdSet);
}
