package com.github.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Dooby Kim
 * @Date 2024/2/21 下午11:46
 * @Version 1.0
 * 前端 -> 后端
 */
@Getter
@Setter
@Builder
public class FoodieUserBO {
    private String username;
    private String password;
    private String confirmPassword;
}
