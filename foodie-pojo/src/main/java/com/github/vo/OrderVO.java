package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2024/4/3 上午12:24
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private String orderId;
    private MerchantOrderVO merchantOrdersVO;
}
