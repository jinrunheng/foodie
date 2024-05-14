package com.github.service.impl.center;

import cn.hutool.core.util.IdUtil;
import com.github.bo.center.OrderItemsCommentBO;
import com.github.mapper.CustomItemCommentMapper;
import com.github.mapper.CustomOrderMapper;
import com.github.mapper.OrderItemMapper;
import com.github.mapper.OrderStatusMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pojo.OrderItem;
import com.github.pojo.OrderStatus;
import com.github.service.center.MyCommentsService;
import com.github.utils.PagedGridResult;
import com.github.vo.MyCommentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/5/7 下午11:34
 * @Version 1.0
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private CustomItemCommentMapper customItemCommentMapper;

    @Resource
    private CustomOrderMapper customOrderMapper;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Override
    public List<OrderItem> queryPendingComments(String orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        return orderItemMapper.select(orderItem);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 item_comment
        for (OrderItemsCommentBO item : commentList) {
            item.setCommentId(IdUtil.getSnowflakeNextIdStr());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        customItemCommentMapper.saveComments(map);
        // 2. 修改订单表为已评价
        customOrderMapper.updateOrderIsCommentForYes(orderId);
        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        final List<MyCommentVO> myCommentVOS = customItemCommentMapper.queryMyComments(map);
        return PagedGridResult.setPagedGrid(myCommentVOS, page);
    }
}
