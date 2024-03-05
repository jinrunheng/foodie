package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/5 下午11:49
 * @Version 1.0
 * @Desc 二级分类 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;
    // 三级分类 VO list
    private List<SubCategoryVO> subCatList;
}
