package com.github.service.impl.center;

import com.github.mapper.OrderItemMapper;
import com.github.pojo.OrderItem;
import com.github.service.center.MyCommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/5/7 下午11:34
 * @Version 1.0
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> queryPendingComments(String orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        return orderItemMapper.select(orderItem);
    }
}
