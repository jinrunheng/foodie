package com.github.config;

import com.github.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Dooby Kim
 * @Date 2024/4/7 下午10:47
 * @Version 1.0
 */
@Component
public class OrderJob {

    @Resource
    private OrderService orderService;

    /**
     * 每小时执行一次定时任务，更新订单状态，将超过 24 小未支付的订单关闭
     * <p>
     * TODO
     * 定时任务有以下几点弊端：
     * 1. 有时间差，10：30 下单，11：00 检查不足 24 h，12：00 检查则超过 24 h
     * 2. 不支持集群
     * 3. 对数据表进行全表搜索，影响性能
     * <p>
     * 优化方案：
     * <p>
     * 使用 MQ 延时队列处理
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();
    }
}
