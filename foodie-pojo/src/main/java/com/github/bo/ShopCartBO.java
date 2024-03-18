package com.github.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2024/3/18 下午11:23
 * @Version 1.0
 * @Desc 购物车 RequestBody 前端 -> 后端
 */
@Getter
@Setter
@ToString
public class ShopCartBO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;
}
