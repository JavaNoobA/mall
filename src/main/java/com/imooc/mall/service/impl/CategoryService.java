package com.imooc.mall.service.impl;

import com.imooc.mall.dao.CategoryMapper;
import com.imooc.mall.pojo.Category;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author pengfei.zhao
 * @date 2020/11/2 21:23
 */
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(c -> Category.ROOT_PARENT_ID.equals(c.getParentId()))
                .map(this::category2vo)
                //.sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categories, categoryVoList);
        return ResponseVo.success(categoryVoList);
    }

    private void findSubCategory(List<Category> categories, List<CategoryVo> categoryVoList) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryList = new ArrayList<>();

            for (Category category : categories) {
                if (Objects.equals(category.getParentId(), categoryVo.getId())) {
                    CategoryVo vo = category2vo(category);
                    subCategoryList.add(vo);
                    categoryVo.setSubCategories(subCategoryList);
                }
                //subCategoryList.sort(Comparator.comparing(CategoryVo::getSortOrder));

                // 递归查询, 知道查到 subCategory 没有上级category 内容
                findSubCategory(categories, subCategoryList);
            }
        }
    }

    public CategoryVo category2vo(Category category) {
        CategoryVo vo = new CategoryVo();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }
}
