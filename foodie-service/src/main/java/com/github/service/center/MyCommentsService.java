package com.github.service.center;

import com.github.bo.center.OrderItemsCommentBO;
import com.github.pojo.OrderItem;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/5/7 下午11:34
 * @Version 1.0
 */

public interface MyCommentsService {
    /**
     * 根据订单 id 查询待评价的商品列表
     *
     * @param orderId
     * @return
     */
    List<OrderItem> queryPendingComments(String orderId);

    /**
     * 保存用户的评论
     *
     * @param orderId
     * @param userId
     * @param commentList
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);
}
