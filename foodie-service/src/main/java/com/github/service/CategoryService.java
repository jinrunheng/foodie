package com.github.service;

import com.github.pojo.Category;
import com.github.vo.CategoryVO;
import com.github.vo.NewItemsVO;

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

    /**
     * 根据一级分类 ID 查询子分类信息
     *
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的 6 条最新商品
     *
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItems(Integer rootCatId);
}
