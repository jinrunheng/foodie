package com.github.service.impl.center;

import com.github.mapper.CustomOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.service.center.MyOrderService;
import com.github.utils.PagedGridResult;
import com.github.vo.MyOrderVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Dooby Kim
 * @Date 2024/4/22 下午11:48
 * @Version 1.0
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Resource
    private CustomOrderMapper customOrderMapper;

    @Override
    public PagedGridResult queryUserOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (!Objects.isNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        List<MyOrderVO> myOrderVOList = customOrderMapper.queryUserOrders(map);
        PageHelper.startPage(page, pageSize);
        return PagedGridResult.setPagedGrid(myOrderVOList, page);
    }
}
