package com.github.utils;

import org.junit.Assert;
import org.junit.Test;

import static com.github.utils.DesensitizationUtil.commonDisplay;

/**
 * @Author Dooby Kim
 * @Date 2024/3/16 下午11:08
 * @Version 1.0
 */
public class DesensitizationUtilTest {
    @Test
    public void test() {
        String name = commonDisplay("kim");
        String mobile = commonDisplay("13900000000");
        String mail = commonDisplay("kim@qq.com");
        String address = commonDisplay("深圳南山软件园1024号");

        Assert.assertEquals(name, "k*m");
        Assert.assertEquals(mobile, "13******000");
        Assert.assertEquals(mail, "ki******com");
        Assert.assertEquals(address, "深圳南******024号");
    }
}