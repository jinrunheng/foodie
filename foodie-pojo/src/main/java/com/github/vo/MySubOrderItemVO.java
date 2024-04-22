package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Dooby Kim
 * @Date 2024/4/22 下午11:31
 * @Version 1.0
 * @Desc 用户中心，我的订单列表嵌套商品 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySubOrderItemVO {
    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;
}
