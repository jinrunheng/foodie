package com.github.controller;

import com.github.bo.AddressBO;
import com.github.pojo.UserAddress;
import com.github.service.AddressService;
import com.github.utils.CustomJSONResult;
import com.github.utils.MobileCheckUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    private static int RECEIVER_MAX_LEN = 12;


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

    @PostMapping("/add")
    @ApiOperation(value = "新增用户地址")
    public CustomJSONResult add(
            @RequestBody AddressBO addressBO
    ) {
        final CustomJSONResult checkResult = checkAddressForm(addressBO);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        addressService.addNewUserAddr(addressBO);
        return CustomJSONResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户地址")
    public CustomJSONResult update(
            @RequestBody AddressBO addressBO
    ) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return CustomJSONResult.errorMap("地址 ID 不能为空！");
        }
        final CustomJSONResult checkResult = checkAddressForm(addressBO);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        addressService.updateUserAddr(addressBO);
        return CustomJSONResult.ok();
    }


    /**
     * 检查用户地址格式
     *
     * @param addressBO
     * @return
     */
    private CustomJSONResult checkAddressForm(AddressBO addressBO) {
        final String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return CustomJSONResult.errorMsg("收货人不能为空!");
        }
        if (receiver.length() > RECEIVER_MAX_LEN) {
            return CustomJSONResult.errorMsg("收货人长度不能超过 12!");
        }
        final String mobile = addressBO.getMobile();
        if (!MobileCheckUtils.check(mobile)) {
            return CustomJSONResult.errorMsg("收货人手机号格式错误!");
        }

        final String province = addressBO.getProvince();
        final String city = addressBO.getCity();
        final String district = addressBO.getDistrict();
        final String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return CustomJSONResult.errorMsg("收获地址信息不能为空！");
        }
        return CustomJSONResult.ok();
    }
}
