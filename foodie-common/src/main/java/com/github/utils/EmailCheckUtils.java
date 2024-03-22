package com.github.utils;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:54
 * @Version 1.0
 */
public class EmailCheckUtils {
    private static final String EMAIL_CHECK_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean check(String email) {
        if (email.matches(EMAIL_CHECK_REGEX)) {
            return true;
        }
        return false;
    }
}
