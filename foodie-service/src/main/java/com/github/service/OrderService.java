package com.github.service;

import com.github.bo.SubmitOrderBO;
import com.github.vo.OrderVO;

/**
 * @Author Dooby Kim
 * @Date 2024/3/27 下午9:59
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 创建订单，返回 orderId
     *
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
