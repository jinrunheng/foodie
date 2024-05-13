package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/5/13 下午11:42
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentVO {

    private String commentId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String itemName;
    private String specName;
    private String itemImg;
}
