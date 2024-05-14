package com.github.controller.center;

import com.github.bo.center.OrderItemsCommentBO;
import com.github.enums.YesOrNo;
import com.github.pojo.Order;
import com.github.pojo.OrderItem;
import com.github.service.center.MyCommentsService;
import com.github.service.center.MyOrderService;
import com.github.utils.CustomJSONResult;
import com.github.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author Dooby Kim
 * @Date 2024/5/7 下午11:43
 * @Version 1.0
 */
@Api(tags = {"用户中心-评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController {

    @Resource
    private MyCommentsService myCommentsService;

    @Resource
    private MyOrderService myOrderService;

    @ApiOperation(value = "查询待评价商品列表")
    @PostMapping("/pending")
    public CustomJSONResult pending(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        final CustomJSONResult checkResult = checkUserOrder(orderId, userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断订单是否有被评论过，如果评论过则直接返回
        final Order myOrder = (Order) checkResult.getData();
        if (myOrder.getIsComment().equals(YesOrNo.YES.type)) {
            return CustomJSONResult.errorMsg("该笔订单已评价过！");
        }

        final List<OrderItem> orderItems = myCommentsService.queryPendingComments(orderId);
        return CustomJSONResult.ok(orderItems);
    }

    @ApiOperation(value = "保存评论列表")
    @PostMapping("/saveList")
    public CustomJSONResult saveList(
            @ApiParam(name = "orderId", value = "订单id")
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @RequestBody List<OrderItemsCommentBO> commentList
    ) {
        final CustomJSONResult checkResult = checkUserOrder(orderId, userId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容 List 不能为空
        if (Objects.isNull(commentList) || commentList.isEmpty()) {
            return CustomJSONResult.errorMsg("评论内容不能为空");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return CustomJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价")
    @PostMapping("/query")
    public CustomJSONResult query(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "page", value = "第几页")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页几条数据")
            @RequestParam Integer pageSize
    ) {
        if (StringUtils.isBlank(userId)) {
            return CustomJSONResult.errorMsg("用户 ID 不能为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        final PagedGridResult pagedGridResult = myCommentsService.queryMyComments(userId, page, pageSize);
        return CustomJSONResult.ok(pagedGridResult);
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
        return CustomJSONResult.ok(order);
    }
}
