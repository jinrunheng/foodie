package com.github.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bo.AddressBO;
import com.github.mapper.UserAddressMapper;
import com.github.pojo.UserAddress;
import com.github.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:12
 * @Version 1.0
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> queryAllUserAddr(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddr(AddressBO addressBO) {
        int isDefault = 0;
        // 1. 判断用户是否有地址，如果没有则设置为默认地址
        final List<UserAddress> userAddresses = queryAllUserAddr(addressBO.getUserId());
        if (userAddresses == null || userAddresses.isEmpty()) {
            isDefault = 1;
        }
        // 2. 保存地址到 DB
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(IdUtil.getSnowflakeNextIdStr());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddr(AddressBO addressBO) {
        final String addressId = addressBO.getAddressId();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
}
