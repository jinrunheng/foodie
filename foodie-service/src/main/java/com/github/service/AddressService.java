package com.github.service;

import com.github.bo.AddressBO;
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

    /**
     * 用户新增地址
     *
     * @param addressBO
     */
    void addNewUserAddr(AddressBO addressBO);

    /**
     * 用户修改地址
     *
     * @param addressBO
     */
    void updateUserAddr(AddressBO addressBO);

    /**
     * 用户删除地址
     *
     * @param userId
     * @param addressId
     */
    void delUserAddr(String userId, String addressId);

    /**
     * 修改地址为用户默认地址
     *
     * @param userId
     * @param addressId
     */
    void updateUserAddrDefault(String userId, String addressId);
}
