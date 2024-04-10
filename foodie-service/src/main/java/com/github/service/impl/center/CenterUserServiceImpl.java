package com.github.service.impl.center;

import com.github.mapper.FoodieUserMapper;
import com.github.pojo.FoodieUser;
import com.github.service.center.CenterUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午10:31
 * @Version 1.0
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Resource
    private FoodieUserMapper userMapper;

    @Override
    public FoodieUser queryUserInfo(String userId) {
        FoodieUser foodieUser = userMapper.selectByPrimaryKey(userId);
        // 隐藏密码信息
        foodieUser.setPassword(null);
        return foodieUser;
    }
}
