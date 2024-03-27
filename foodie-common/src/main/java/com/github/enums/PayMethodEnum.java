package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/3/27 下午9:50
 * @Version 1.0
 * @Desc 支付方式
 */
public enum PayMethodEnum {

    WEIXIN_PAY(1, "微信支付"),
    ALIPAY(2, "支付宝支付");

    public final Integer type;
    public final String value;

    PayMethodEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
