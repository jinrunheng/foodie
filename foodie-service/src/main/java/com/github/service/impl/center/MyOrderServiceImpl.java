package com.github.service.impl.center;

import com.github.enums.OrderStatusEnum;
import com.github.enums.YesOrNo;
import com.github.mapper.CustomOrderMapper;
import com.github.mapper.OrderMapper;
import com.github.mapper.OrderStatusMapper;
import com.github.pagehelper.PageHelper;
import com.github.pojo.Order;
import com.github.pojo.OrderStatus;
import com.github.service.center.MyOrderService;
import com.github.utils.PagedGridResult;
import com.github.vo.MyOrderVO;
import com.github.vo.OrderStatusCountsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author Dooby Kim
 * @Date 2024/4/22 下午11:48
 * @Version 1.0
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Resource
    private CustomOrderMapper customOrderMapper;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public PagedGridResult queryUserOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (!Objects.isNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        List<MyOrderVO> myOrderVOList = customOrderMapper.queryUserOrders(map);
        PageHelper.startPage(page, pageSize);
        return PagedGridResult.setPagedGrid(myOrderVOList, page);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Override
    public Order queryMyOrder(String orderId, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("id", orderId);
        map.put("userId", userId);

        return customOrderMapper.queryMyOrder(map);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        final Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        final int result = orderStatusMapper.updateByExampleSelective(updateOrder, example);
        return result == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String orderId, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("id", orderId);
        map.put("userId", userId);
        final int result = customOrderMapper.deleteOrder(map);
        return result == 1;
    }

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        final int waitPayCounts = customOrderMapper.getMyOrderStatusCount(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        final int waitDeliverCounts = customOrderMapper.getMyOrderStatusCount(map);
        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        final int waitReceiveCounts = customOrderMapper.getMyOrderStatusCount(map);
        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        final int waitCommentCounts = customOrderMapper.getMyOrderStatusCount(map);
        final OrderStatusCountsVO orderStatusCountsVO = new OrderStatusCountsVO()
                .setWaitPayCounts(waitPayCounts)
                .setWaitDeliverCounts(waitDeliverCounts)
                .setWaitReceiveCounts(waitReceiveCounts)
                .setWaitCommentCounts(waitCommentCounts);
        return orderStatusCountsVO;
    }
}
