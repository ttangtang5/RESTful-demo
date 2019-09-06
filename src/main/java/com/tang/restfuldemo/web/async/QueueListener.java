package com.tang.restfuldemo.web.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Description 模拟应用一 监听下单完成队列
 * ApplicationListener<ContextRefreshedEvent>: 表示监听spring初始化完成事件
 * @Author tang
 * @Date 2019-08-22 17:43
 * @Version 1.0
 **/
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    private MqQueue mqQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 用线程 避免阻塞
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mqQueue.getEndOrder())) {
                    logger.info("监听到订单返回队列的新信息");
                    DeferredResult<String> deferredResult = deferredResultHolder.getMap().get(mqQueue.getEndOrder());
                    // 将需返回前端的信息 设置到deferredResult中
                    deferredResult.setResult("create order success");

                    // 处理掉的移除
                    mqQueue.setEndOrder(null);
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
