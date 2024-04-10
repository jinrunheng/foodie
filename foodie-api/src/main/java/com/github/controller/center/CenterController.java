package com.github.controller.center;

import com.github.pojo.FoodieUser;
import com.github.service.center.CenterUserService;
import com.github.utils.CustomJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2024/4/10 下午10:12
 * @Version 1.0
 */
@Api(tags = {"用户中心相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

    @Resource
    private CenterUserService centerUserService;

    @ApiOperation(value = "根据用户 ID，查询用户信息")
    @GetMapping("userInfo")
    public CustomJSONResult userInfo(@RequestParam String userId) {
        final FoodieUser foodieUser = centerUserService.queryUserInfo(userId);
        return CustomJSONResult.ok(foodieUser);
    }

}
