package com.github.mapper;

import com.github.vo.CategoryVO;
import com.github.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/5 下午11:39
 * @Version 1.0
 */
public interface CustomCategoryMapper {
    List<CategoryVO> getSubCatList(Integer rootCatId);

    List<NewItemsVO> getSixNewItems(@Param("paramsMap") Map<String, Object> map);
}
