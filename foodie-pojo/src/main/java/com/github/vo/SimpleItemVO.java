package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Dooby Kim
 * @Date 2024/3/11 下午11:30
 * @Version 1.0
 * @Desc 6 个最新商品
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleItemVO {
    private String itemId;
    private String itemName;
    private String itemUrl;
}
