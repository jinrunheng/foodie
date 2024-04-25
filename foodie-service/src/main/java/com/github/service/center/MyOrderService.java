package com.github.service.center;

import com.github.utils.PagedGridResult;

/**
 * @Author Dooby Kim
 * @Date 2024/4/22 下午11:48
 * @Version 1.0
 */
public interface MyOrderService {

    /**
     * 查询用户对应的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryUserOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 订单状态 -> 商家发货
     *
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);
}
