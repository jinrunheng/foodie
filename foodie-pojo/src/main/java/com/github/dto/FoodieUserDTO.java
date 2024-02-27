package com.github.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2024/2/26 下午11:53
 * @Version 1.0
 */
@Getter
@Setter
public class FoodieUserDTO {
    /**
     * 主键id 用户id
     */
    private String id;

    /**
     * 用户名 用户名
     */
    private String username;


    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名 真实姓名
     */
    private String realname;

    /**
     * 头像 头像
     */
    private String face;


    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;
}
