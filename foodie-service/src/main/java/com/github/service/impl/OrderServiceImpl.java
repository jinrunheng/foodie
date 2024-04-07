package com.github.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bo.SubmitOrderBO;
import com.github.enums.OrderStatusEnum;
import com.github.enums.YesOrNo;
import com.github.mapper.CustomOrderMapper;
import com.github.mapper.OrderItemMapper;
import com.github.mapper.OrderMapper;
import com.github.mapper.OrderStatusMapper;
import com.github.pojo.*;
import com.github.service.AddressService;
import com.github.service.ItemService;
import com.github.service.OrderService;
import com.github.vo.MerchantOrderVO;
import com.github.vo.OrderVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Dooby Kim
 * @Date 2024/3/27 下午9:59
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private CustomOrderMapper customOrderMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AddressService addressService;

    @Resource
    private ItemService itemService;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        // 邮费设置为 0；即包邮
        Integer postAmount = 0;

        final UserAddress userAddress = addressService.queryUserAddr(userId, addressId);

        // 保存订单数据
        Order order = new Order();
        order.setId(IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setReceiverName(userAddress.getReceiver());
        order.setReceiverMobile(userAddress.getMobile());
        order.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        order.setPostAmount(postAmount);
        order.setPayMethod(payMethod);
        order.setLeftMsg(leftMsg);
        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());
        final String[] itemSplitIds = itemSpecIds.split("\\,");
        Integer totalAmount = 0;
        Integer realPayAmount = 0;
        for (String itemSpecId : itemSplitIds) {
            // TODO 整合 Redis 后，商品购买数量从 Redis 购物车中获取
            int buyCounts = 1;
            final ItemSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;
            // 根据商品 ID，获取商品信息以及商品图片
            final String itemId = itemSpec.getItemId();
            final Item item = itemService.queryItemById(itemId);
            final String itemImgUrl = itemService.queryItemMainImgById(itemId);
            // 保存子订单数据到 DB
            OrderItem subOrderItem = new OrderItem();
            subOrderItem.setId(IdUtil.getSnowflakeNextIdStr());
            subOrderItem.setOrderId(order.getId());
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(itemImgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemMapper.insert(subOrderItem);
            // 减库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        // 保存订单
        customOrderMapper.createOrder(order);

        // 保存到订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(order.getId());
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 构建商户订单，用于传给支付中心
        MerchantOrderVO merchantOrderVO = new MerchantOrderVO();
        merchantOrderVO.setMerchantOrderId(order.getId());
        merchantOrderVO.setMerchantUserId(userId);
        merchantOrderVO.setAmount(realPayAmount + postAmount);
        merchantOrderVO.setPayMethod(payMethod);

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(order.getId());
        orderVO.setMerchantOrdersVO(merchantOrderVO);
        return orderVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void closeOrder() {
        // 查询所有未付款订单，判断时间是否超时，超时时间为 24 h，超时则关闭订单
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        final List<OrderStatus> select = orderStatusMapper.select(queryOrder);
        for (OrderStatus orderStatus : select) {
            // 获取订单创建时间
            final Date createdTime = orderStatus.getCreatedTime();
            // 与当前时间进行对比
            Date currentDate = new Date();
            long diffInHours = TimeUnit.MILLISECONDS.toHours(currentDate.getTime() - createdTime.getTime());
            if (diffInHours > 24) {
                // 如果订单超过 24 h，则关闭订单
                OrderStatus close = new OrderStatus();
                close.setOrderId(orderStatus.getOrderId());
                close.setOrderStatus(OrderStatusEnum.CLOSE.type);
                close.setCloseTime(new Date());
                orderStatusMapper.updateByPrimaryKeySelective(close);
            }
        }
    }
}
