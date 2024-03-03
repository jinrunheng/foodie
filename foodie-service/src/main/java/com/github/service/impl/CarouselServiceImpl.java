package com.github.service.impl;

import com.github.mapper.CarouselMapper;
import com.github.pojo.Carousel;
import com.github.service.CarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午5:41
 * @Version 1.0
 */
@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").asc();
        final Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        return carouselMapper.selectByExample(example);
    }
}
