package com.github.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Dooby Kim
 * @Date 2024/3/26 下午11:30
 * @Version 1.0
 * @Desc 创建订单 BO
 */
@Getter
@Setter
@ToString
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
