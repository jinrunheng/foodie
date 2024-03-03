package com.github.controller;

import com.github.enums.YesOrNo;
import com.github.pojo.Carousel;
import com.github.service.CarouselService;
import com.github.utils.CustomJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/3 下午5:46
 * @Version 1.0
 * @Desc 首页
 */
@Api(tags = {"用于首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图列表")
    public CustomJSONResult carousel() {
        final List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        return CustomJSONResult.ok(carousels);
    }
}
