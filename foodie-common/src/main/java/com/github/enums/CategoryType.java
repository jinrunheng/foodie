package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午7:00
 * @Version 1.0
 */
public enum CategoryType {
    LEVEL1(1, "一级分类"),

    LEVEL2(2, "二级分类"),

    LEVEL3(3, "三级分类");

    public final Integer type;
    public final String value;

    CategoryType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
