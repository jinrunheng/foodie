package com.github.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Dooby Kim
 * @Date 2024/4/3 上午12:24
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private String orderId;
    private MerchantOrderVO merchantOrdersVO;
}
