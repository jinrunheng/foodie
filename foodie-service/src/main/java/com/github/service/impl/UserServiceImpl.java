package com.github.service.impl;

import com.github.mapper.FoodieUsersMapper;
import com.github.pojo.FoodieUsers;
import com.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:14
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FoodieUsersMapper usersMapper;

    @Override
    public boolean queryUserNameExist(String username) {
        Example userExample = new Example(FoodieUsers.class);
        final Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        final FoodieUsers foodieUser = usersMapper.selectOneByExample(userExample);
        return foodieUser != null;
    }
}
