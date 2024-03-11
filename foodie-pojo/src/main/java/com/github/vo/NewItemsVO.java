package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/11 下午11:25
 * @Version 1.0
 * @Desc 最新商品 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewItemsVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private List<SimpleItemVO> simpleItemList;
}
