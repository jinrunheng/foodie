package com.github.service.impl.center;

import com.github.enums.OrderStatusEnum;
import com.github.mapper.CustomOrderMapper;
import com.github.mapper.OrderStatusMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pojo.OrderStatus;
import com.github.service.center.MyOrderService;
import com.github.utils.PagedGridResult;
import com.github.vo.MyOrderVO;
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
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }
}
