package com.github.controller.center;

import com.github.pojo.Order;
import com.github.service.center.MyOrderService;
import com.github.utils.CustomJSONResult;
import com.github.utils.PagedGridResult;
import com.github.vo.OrderStatusCountsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

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

    @ApiOperation(value = "用户确认收获")
    @PostMapping("/confirmReceive")
    public CustomJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        final CustomJSONResult checkResult = checkUserOrder(orderId, userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        final boolean result = myOrderService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return CustomJSONResult.errorMsg("订单确认收货失败！");
        }
        return CustomJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单")
    @PostMapping("/delete")
    public CustomJSONResult deleteOrder(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        final CustomJSONResult checkResult = checkUserOrder(orderId, userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        final boolean result = myOrderService.deleteOrder(orderId, userId);
        if (!result) {
            return CustomJSONResult.errorMsg("删除订单失败！");
        }
        return CustomJSONResult.ok();
    }

    @ApiOperation(value = "获得订单状态数状态概况")
    @PostMapping("/statusCounts")
    public CustomJSONResult statusCounts(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            return CustomJSONResult.errorMsg("用户 ID 不能为空！");
        }
        final OrderStatusCountsVO orderStatusCounts = myOrderService.getOrderStatusCounts(userId);
        return CustomJSONResult.ok(orderStatusCounts);
    }

    @ApiOperation(value = "查询订单动向")
    @PostMapping("/trend")
    public CustomJSONResult trend(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
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

        final PagedGridResult ordersTrend = myOrderService.getOrdersTrend(userId, page, pageSize);
        return CustomJSONResult.ok(ordersTrend);
    }

    /**
     * 验证用户和订单是否有关联，避免非法用户调用
     *
     * @return
     */
    private CustomJSONResult checkUserOrder(String orderId, String userId) {
        final Order order = myOrderService.queryMyOrder(orderId, userId);
        if (Objects.isNull(order)) {
            return CustomJSONResult.errorMsg("订单不存在");
        }
        return CustomJSONResult.ok();
    }
}
