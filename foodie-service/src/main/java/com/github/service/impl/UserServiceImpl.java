package com.github.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.bo.FoodieUserBO;
import com.github.enums.GenderEnum;
import com.github.mapper.FoodieUserMapper;
import com.github.pojo.FoodieUser;
import com.github.service.UserService;
import com.github.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:14
 * @Version 1.0
 */
@Service
@Slf4j
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FoodieUser createUser(FoodieUserBO foodieUserBO) {
        FoodieUser foodieUser = new FoodieUser();
        // hutool 工具类生成雪花ID
        foodieUser.setId(IdUtil.getSnowflakeNextIdStr());
        foodieUser.setUsername(foodieUserBO.getUsername());
        try {
            foodieUser.setPassword(MD5Utils.getMD5Str(foodieUserBO.getPassword())); // 加密
        } catch (Exception e) {
            log.error("用户密码加密错误", e);
        }
        // 默认用户昵称同用户名
        foodieUser.setNickname(foodieUserBO.getUsername());
        foodieUser.setFace(null); // 默认头像为空
        try {
            // 默认生日
            foodieUser.setBirthday(DateUtils.parseDate("1900-01-01", "yyyy-MM-dd"));
        } catch (ParseException e) {
            log.error("用户设置生日错误", e);
        }
        // 默认性别保密
        foodieUser.setSex(GenderEnum.secret.type);
        foodieUser.setCreatedTime(new Date());
        foodieUser.setUpdatedTime(new Date());
        userMapper.insert(foodieUser);
        return foodieUser;
    }
}
