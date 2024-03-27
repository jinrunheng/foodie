package com.github.service;

import com.github.bo.SubmitOrderBO;

/**
 * @Author Dooby Kim
 * @Date 2024/3/27 下午9:59
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param submitOrderBO
     */
    void createOrder(SubmitOrderBO submitOrderBO);
}
