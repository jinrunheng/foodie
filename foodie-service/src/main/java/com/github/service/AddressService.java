package com.github.service;

import com.github.pojo.UserAddress;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:11
 * @Version 1.0
 */
public interface AddressService {
    /**
     * 根据用户 ID 查询用户地址列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> queryAllUserAddr(String userId);
}
