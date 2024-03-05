package com.github.mapper;

import com.github.vo.CategoryVO;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/5 下午11:39
 * @Version 1.0
 */
public interface CustomCategoryMapper {
    List<CategoryVO> getSubCatList(Integer rootCatId);
}
