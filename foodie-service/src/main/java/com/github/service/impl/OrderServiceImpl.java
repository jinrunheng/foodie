package com.github.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bo.SubmitOrderBO;
import com.github.enums.YesOrNo;
import com.github.mapper.OrderMapper;
import com.github.pojo.Order;
import com.github.pojo.UserAddress;
import com.github.service.AddressService;
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

        // 1. 保存订单数据
        Order order = new Order();
        order.setId(IdUtil.getSnowflakeNextIdStr());
        order.setReceiverName(userAddress.getReceiver());
        order.setReceiverMobile(userAddress.getMobile());
        order.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        // order.setTotalAmount();
        // order.setRealPayAmount();
        order.setPostAmount(postAmount);
        order.setPayMethod(payMethod);
        order.setLeftMsg(leftMsg);
        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());


    }
}
