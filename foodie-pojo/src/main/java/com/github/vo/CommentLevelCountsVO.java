package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Dooby Kim
 * @Date 2024/3/14 下午11:03
 * @Version 1.0
 * @Desc 用于展示商品评价数量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLevelCountsVO {
    Integer totalCounts;
    Integer goodCounts;
    Integer normalCounts;
    Integer badCounts;
}
