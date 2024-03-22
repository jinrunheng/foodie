package com.github.service.impl;

import com.github.mapper.UserAddressMapper;
import com.github.pojo.UserAddress;
import com.github.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
