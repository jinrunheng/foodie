package com.github.service;

import com.github.pojo.Carousel;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午5:39
 * @Version 1.0
 * @Desc 轮播图
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     *
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
