package com.github.utils;

/**
 * @Author Dooby Kim
 * @Date 2024/4/18 下午11:25
 * @Version 1.0
 */
public class ImgSuffixCheckUtil {
    /**
     * 用于判断图片后缀是否符合要求
     *
     * @param imgSuffix
     * @return
     */
    public static boolean isIllegal(String imgSuffix) {
        if (imgSuffix.equalsIgnoreCase("png")
                || imgSuffix.equalsIgnoreCase("jpg")
                || imgSuffix.equalsIgnoreCase("jpeg")) {
            return true;
        }
        return false;
    }
}
