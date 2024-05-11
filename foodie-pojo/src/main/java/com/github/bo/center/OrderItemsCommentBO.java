package com.github.bo.center;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2024/5/11 下午11:39
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemsCommentBO {
    private String commentId;
    private String itemId;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer commentLevel;
    private String content;
}
