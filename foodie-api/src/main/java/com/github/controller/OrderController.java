package com.github.controller;

import com.github.bo.SubmitOrderBO;
import com.github.enums.PayMethodEnum;
import com.github.utils.CustomJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Dooby Kim
 * @Date 2024/3/26 下午11:27
 * @Version 1.0
 */
@Api(tags = {"订单相关 API 接口"})
@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

    @PostMapping("/create")
    @ApiOperation(value = "用户下单")
    public CustomJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO
    ) {
        if (submitOrderBO.getPayMethod() != PayMethodEnum.WEIXIN_PAY.type &&
                submitOrderBO.getPayMethod() != PayMethodEnum.ALIPAY.type
        ) {
            return CustomJSONResult.errorMsg("不支持的支付方式！");
        }
        log.info("submitOrderBO", submitOrderBO);
        // TODO
        // 1. 创建订单
        // 2. 创建订单后，移除购物车中已结算的商品
        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        return CustomJSONResult.ok();
    }
}
