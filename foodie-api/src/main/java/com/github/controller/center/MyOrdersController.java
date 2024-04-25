package com.github.controller.center;

import com.github.service.center.MyOrderService;
import com.github.utils.CustomJSONResult;
import com.github.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2024/4/23 上午12:06
 * @Version 1.0
 */
@Api(tags = {"用户中心-我的订单相关接口"})
@RequestMapping("myorders")
@RestController
@Slf4j
public class MyOrdersController {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    @Resource
    private MyOrderService myOrderService;

    @PostMapping("/query")
    @ApiOperation(value = "查询订单列表")
    public CustomJSONResult query(
            @ApiParam(name = "userId", value = "用户 ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态")
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "第几页")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页几条数据")
            @RequestParam Integer pageSize

    ) {
        if (StringUtils.isBlank(userId)) {
            return CustomJSONResult.errorMsg("用户 ID 不能为空!");
        }
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        final PagedGridResult pagedGridResult = myOrderService.queryUserOrders(userId, orderStatus, page, pageSize);
        return CustomJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "模拟-商家发货")
    @GetMapping("/deliver")
    public CustomJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam String orderId
    ) {
        if (StringUtils.isBlank(orderId)) {
            return CustomJSONResult.errorMsg("订单 ID 不能为空");
        }
        myOrderService.updateDeliverOrderStatus(orderId);
        return CustomJSONResult.ok();
    }
}
