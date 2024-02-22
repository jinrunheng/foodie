package com.github.controller;

import com.github.bo.FoodieUserBO;
import com.github.pojo.FoodieUser;
import com.github.service.UserService;
import com.github.utils.CustomJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:21
 * @Version 1.0
 * 用于登陆验证
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public CustomJSONResult queryUsernameIsExist(@RequestParam String username) {
        // 判断用户名是否为空
        if (StringUtils.isBlank(username)) {
            return CustomJSONResult.errorMsg("用户名不能为空");
        }

        if (exist(username)) return CustomJSONResult.errorMsg("用户名已存在");
        // 请求成功
        return CustomJSONResult.ok();
    }

    @PostMapping("/regist")
    public CustomJSONResult regist(@RequestBody FoodieUserBO userBO) {
        final String username = userBO.getUsername();
        final String password = userBO.getPassword();
        final String confirmPassword = userBO.getConfirmPassword();
        // 1. 判断用户名与密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return CustomJSONResult.errorMsg("用户名或密码不能为空!");
        }
        // 2. 查询用户名是否存在
        if (exist(username)) return CustomJSONResult.errorMsg("用户名已存在");
        // 3. 密码长度限制，不能少于 6 位
        if (password.length() < 6) {
            return CustomJSONResult.errorMsg("密码长度不能少于 6 位");
        }
        // 4. 判断两次输入密码是否一致
        if (!Objects.equals(password, confirmPassword)) {
            return CustomJSONResult.errorMsg("两次密码输入不一致");
        }

        final FoodieUser user = userService.createUser(userBO);
        return CustomJSONResult.ok(user);
    }

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    private boolean exist(String username) {
        final boolean exist = userService.queryUserNameIsExist(username);
        if (exist) {
            return true;
        }
        return false;
    }
}
