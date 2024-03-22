package com.github.utils;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:54
 * @Version 1.0
 */
public class MobileCheckUtils {

    private static final int CORRECT_MOBILE_LEN = 11;
    private static final String MOBILE_CHECK_REGEX = "^1[3-9]\\d{9}$";

    public static boolean check(String mobile) {
        if (mobile.length() != CORRECT_MOBILE_LEN || !mobile.matches(MOBILE_CHECK_REGEX)) {
            return false;
        }
        return true;
    }
}
