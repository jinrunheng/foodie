package com.github.service;

/**
 * @Author Dooby Kim
 * @Date 2024/2/20 上午1:12
 * @Version 1.0
 */
public interface UserService {

    /**
     * （注册）判断用户名是否存在
     *
     * @return
     */
    boolean queryUserNameIsExist(String username);
}
