package com.github.controller;

import com.github.pojo.UserAddress;
import com.github.service.AddressService;
import com.github.utils.CustomJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/22 下午10:04
 * @Version 1.0
 */
@Api(tags = {"地址相关 API 接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    @ApiOperation(value = "根据用户 ID，查询用户地址列表")
    public CustomJSONResult list(
            @RequestParam String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            return CustomJSONResult.errorMsg("用户 ID 不能为空!");
        }

        final List<UserAddress> userAddresses = addressService.queryAllUserAddr(userId);
        return CustomJSONResult.ok(userAddresses);
    }

}
