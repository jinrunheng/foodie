package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/3/14 下午11:15
 * @Version 1.0
 * @Desc 商品评价等级
 */
public enum CommentLevel {
    GOOD(1, "好评"),

    NORMAL(2, "中评"),

    BAD(3, "差评");

    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
