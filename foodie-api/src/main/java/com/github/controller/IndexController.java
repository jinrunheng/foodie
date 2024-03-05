package com.github.controller;

import com.github.enums.YesOrNo;
import com.github.pojo.Carousel;
import com.github.pojo.Category;
import com.github.service.CarouselService;
import com.github.service.CategoryService;
import com.github.utils.CustomJSONResult;
import com.github.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图列表")
    public CustomJSONResult carousel() {
        final List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
        return CustomJSONResult.ok(carousels);
    }


    // TODO
    // 首页分类展示：
    // 1. 第一次刷新主页查询大分类
    // 2. 如果鼠标移动到大分类上，则加载其子分类的内容；如果子分类已经存在，则无需加载
    @GetMapping("/cats")
    @ApiOperation(value = "获取商品分类")
    public CustomJSONResult cats() {
        final List<Category> categories = categoryService.queryAllRootLevelCat();
        return CustomJSONResult.ok(categories);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取商品子分类")
    public CustomJSONResult subCat(
            @PathVariable
            @ApiParam(name = "rootCatId", value = "一级分类 ID", required = true)
                    Integer rootCatId) {

        if (Objects.isNull(rootCatId)) {
            return CustomJSONResult.errorMsg("一级分类 ID 为空！");
        }
        final List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return CustomJSONResult.ok(subCatList);
    }
}
