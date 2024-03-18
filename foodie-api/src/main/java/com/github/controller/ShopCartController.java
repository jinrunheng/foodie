package com.github.controller;

import com.github.bo.ShopCartBO;
import com.github.utils.CustomJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Dooby Kim
 * @Date 2024/3/18 下午8:22
 * @Version 1.0
 * @Desc 购物车
 */
@Api(tags = {"用于购物车的相关接口"})
@RestController
@RequestMapping("shopcart")
@Slf4j
public class ShopCartController {

    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("/add")
    public CustomJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopCartBO shopCartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return CustomJSONResult.errorMsg("用户 ID 不能为空!");
        }
        log.info("shopCartBO:{}", shopCartBO);
        // TODO: 用户在登陆的情况下，添加商品到购物车，会同时在后端同步购物车到 Redis
        return CustomJSONResult.ok();
    }

}
