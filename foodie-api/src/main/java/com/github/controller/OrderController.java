package com.github.controller;

import com.github.bo.SubmitOrderBO;
import com.github.enums.OrderStatusEnum;
import com.github.enums.PayMethodEnum;
import com.github.service.OrderService;
import com.github.utils.CookieUtils;
import com.github.utils.CustomJSONResult;
import com.github.vo.MerchantOrderVO;
import com.github.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    // 购物车 Cookie
    private static final String FOODIE_SHOPCART = "shopcart";

    // 微信支付成功 -> 支付中心 -> 后台
    // payReturnUrl 为支付中心调用后台接口的 returnUrl
    private static final String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
    // 支付中心调用地址
    private static final String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @ApiOperation(value = "用户下单")
    public CustomJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (submitOrderBO.getPayMethod() != PayMethodEnum.WEIXIN_PAY.type &&
                submitOrderBO.getPayMethod() != PayMethodEnum.ALIPAY.type
        ) {
            return CustomJSONResult.errorMsg("不支持的支付方式！");
        }
        log.info("submitOrderBO", submitOrderBO);
        // TODO
        // 1. 创建订单
        final OrderVO orderVO = orderService.createOrder(submitOrderBO);
        final String orderId = orderVO.getOrderId();


        // 2. 创建订单后，移除购物车中已结算的商品
        // TODO 整合 Redis 后，完善购物车中的已结算商品移除，并同步前端 Cookie
        // CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrderVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrderVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<CustomJSONResult> customJSONResultResponseEntity = restTemplate.postForEntity(paymentUrl, entity, CustomJSONResult.class);
        final CustomJSONResult paymentResult = customJSONResultResponseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            return CustomJSONResult.errorMsg("支付中心订单创建失败！");
        }
        return CustomJSONResult.ok(orderId);
    }

    /**
     * 微信支付系统通知-->支付中心--->通知后台系统
     *
     * @param merchantOrderId
     * @return
     */
    @ApiOperation(value = "支付中心通知")
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}
