package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/3/15 下午9:58
 * @Version 1.0
 * @Desc 商品评价
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;
}
