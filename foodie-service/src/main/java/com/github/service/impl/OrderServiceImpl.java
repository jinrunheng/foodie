package com.github.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bo.SubmitOrderBO;
import com.github.enums.YesOrNo;
import com.github.mapper.OrderItemMapper;
import com.github.mapper.OrderMapper;
import com.github.pojo.*;
import com.github.service.AddressService;
import com.github.service.ItemService;
import com.github.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/3/27 下午9:59
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AddressService addressService;

    @Resource
    private ItemService itemService;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createOrder(SubmitOrderBO submitOrderBO) {
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
        }
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        // 保存订单
        orderMapper.insert(order);
    }
}
