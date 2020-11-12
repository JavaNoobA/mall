package com.imooc.mall.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Category {
    public static final Integer ROOT_PARENT_ID = 0;

    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

}