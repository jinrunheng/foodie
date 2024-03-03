package com.github.service;

import com.github.pojo.Category;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午6:56
 * @Version 1.0
 * @Desc 分类
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     *
     * @return
     */
    List<Category> queryAllRootLevelCat();
}
