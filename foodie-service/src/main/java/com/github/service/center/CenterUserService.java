package com.github.service.center;

import com.github.pojo.FoodieUser;

/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午10:30
 * @Version 1.0
 */
public interface CenterUserService {

    /**
     * 根据用户 ID 查询用户信息
     *
     * @param userId
     * @return
     */
    FoodieUser queryUserInfo(String userId);
}
