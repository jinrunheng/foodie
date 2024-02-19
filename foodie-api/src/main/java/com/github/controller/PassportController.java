package com.github.controller;

import com.github.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/usernameExist")
    public int queryUsernameExist(@RequestParam String username) {
        // 判断用户名是否为空
        if (StringUtils.isBlank(username)) {
            return 500; // 错误码
        }

        final boolean exist = userService.queryUserNameExist(username);
        if (exist) {
            return 500;
        }

        // 请求成功
        return 200;
    }
}
