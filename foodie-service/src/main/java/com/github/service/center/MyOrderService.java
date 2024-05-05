package com.github.service.center;

import com.github.pojo.Order;
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

    /**
     * 查询我的订单
     *
     * @param orderId
     * @param userId
     * @return
     */
    Order queryMyOrder(String orderId, String userId);

    /**
     * 更新订单状态为确认收货(订单状态表)
     *
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单(订单表，逻辑删除)
     *
     * @param orderId
     * @param userId
     * @return
     */
    boolean deleteOrder(String orderId, String userId);
}
