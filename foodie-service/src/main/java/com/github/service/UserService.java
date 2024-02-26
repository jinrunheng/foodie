package com.github.service;

import com.github.bo.FoodieUserBO;
import com.github.pojo.FoodieUser;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:12
 * @Version 1.0
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @return
     */
    boolean queryUserNameIsExist(String username);

    /**
     * 创建用户
     *
     * @param foodieUserBO
     * @return
     */
    FoodieUser createUser(FoodieUserBO foodieUserBO);

    /**
     * 查询用户是否存在
     *
     * @param username
     * @param password
     * @return
     */
    FoodieUser queryUserForLogin(String username, String password);
}
