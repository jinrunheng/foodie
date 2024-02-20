package com.github.service.impl;

import com.github.mapper.FoodieUserMapper;
import com.github.pojo.FoodieUser;
import com.github.service.UserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:14
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private FoodieUserMapper userMapper;

    @Override
    public boolean queryUserNameIsExist(String username) {

        Example userExample = new Example(FoodieUser.class);
        final Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        final FoodieUser foodieUser = userMapper.selectOneByExample(userExample);
        return foodieUser != null;
    }
}
