package com.github.service.impl.center;

import cn.hutool.core.bean.BeanUtil;
import com.github.bo.center.CenterUserBO;
import com.github.mapper.FoodieUserMapper;
import com.github.pojo.FoodieUser;
import com.github.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FoodieUser updateUserInfo(String userId, CenterUserBO userBO) {
        FoodieUser updateUser = new FoodieUser();
        BeanUtils.copyProperties(userBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        userMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FoodieUser updateUserFace(String userId, String faceUrl) {
        FoodieUser updateUser = new FoodieUser();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());
        userMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }
}
