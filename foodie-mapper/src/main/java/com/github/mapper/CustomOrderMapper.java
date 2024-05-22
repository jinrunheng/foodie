package com.github.mapper;

import com.github.pojo.Order;
import com.github.pojo.OrderStatus;
import com.github.vo.MyOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/31 下午10:14
 * @Version 1.0
 */
public interface CustomOrderMapper {
    void createOrder(Order order);

    List<MyOrderVO> queryUserOrders(@Param("paramsMap") Map<String, Object> map);

    Order queryMyOrder(@Param("paramsMap") Map<String, String> map);

    int deleteOrder(@Param("paramsMap") Map<String, String> map);

    void updateOrderIsCommentForYes(@Param("orderId") String orderId);

    int getMyOrderStatusCount(@Param("paramsMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}
