package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/2/22 上午12:14
 * @Version 1.0
 * @Desc 性别枚举
 */
public enum GenderEnum {
    woman(0, "女"),

    man(1, "男"),

    secret(2, "保密");

    public final Integer type;
    public final String value;

    GenderEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
