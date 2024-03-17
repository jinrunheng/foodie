package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2024/3/17 下午2:02
 * @Version 1.0
 * @Desc 用于展示商品搜索列表结果
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price; // 以分为单位
}
