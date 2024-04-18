package com.github.service.center;

import com.github.bo.center.CenterUserBO;
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

    /**
     * 根据用户 ID，修改用户信息
     *
     * @param userId
     * @param userBO
     */
    FoodieUser updateUserInfo(String userId, CenterUserBO userBO);

    /**
     * 更新用户头像
     *
     * @param userId
     * @param faceUrl
     * @return
     */
    FoodieUser updateUserFace(String userId, String faceUrl);
}
