package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Dooby Kim
 * @Date 2024/3/5 下午11:52
 * @Version 1.0
 * @Desc 三级分类 VO
 * Mybatis 务必要有无参构造器
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private Integer subType;
    private Integer subFatherId;
}
