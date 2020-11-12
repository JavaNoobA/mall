package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author pengfei.zhao
 * @date 2020/11/12 18:14
 */
public interface IProductService {

    ResponseVo<PageInfo> list(Integer categoryId, int pageNum, int pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);
}
