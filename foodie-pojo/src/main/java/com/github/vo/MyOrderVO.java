package com.github.vo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/4/22 下午11:28
 * @Version 1.0
 * @Desc 用户中心，我的订单列表 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyOrderVO {
    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Integer orderStatus;
    private List<MySubOrderItemVO> subOrderItemList;
}
