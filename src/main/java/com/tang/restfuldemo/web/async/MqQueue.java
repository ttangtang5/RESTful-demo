package com.tang.restfuldemo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description 模拟消息中间件
 * @Author tang
 * @Date 2019-08-22 17:25
 * @Version 1.0
 **/
@Component
public class MqQueue {

    Logger logger = LoggerFactory.getLogger(MqQueue.class);

    /**
     * 模拟下单队列
     */
    private String startOrder;

    /**
     * 模拟下单返回队列
     */
    private String endOrder;

    public String getStartOrder() {
        return startOrder;
    }

    /**
     * 表示订单系统，消费下单队列。并处理订单
     * @param startOrder
     */
    public void setStartOrder(String startOrder) throws InterruptedException {
        this.startOrder = startOrder;
        logger.info("应用二，处理订单");
        Thread.sleep(1000);
        this.endOrder = startOrder;
        logger.info("应用二，订单处理完成，加入消息队列");
    }

    public String getEndOrder() {
        return endOrder;
    }

    public void setEndOrder(String endOrder) {
        this.endOrder = endOrder;
    }
}
