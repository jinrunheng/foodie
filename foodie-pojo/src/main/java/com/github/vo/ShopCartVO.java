package com.github.vo;

import lombok.*;

/**
 * @Author Dooby Kim
 * @Date 2024/3/19 下午10:34
 * @Version 1.0
 * @Desc 后端 -> 前端 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartVO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
