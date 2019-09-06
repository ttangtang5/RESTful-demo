package com.tang.restfuldemo.web.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Description 异步resetFul
 *  异步并不会对前端有其他影响，主要提供服务器的吞吐量。 异步的interceptor需特殊配置:WebMvcConfigurer中的
 *  1、使用Callable 实现简单的异步api
 *  2、使用DeferredResult 实现复杂的异步api，eg：下单情景中，应用1接收前端请求，应用2处理订单。两个应用之间还存在消息中间件
 *  3、关于异步相关配置，实现WebMvcConfigurer中的configureAsyncSupport方法（WebMvcConfigurer mvc的配置）
 * @Author tang
 * @Date 2019-08-22 16:43
 * @Version 1.0
 **/
@RestController
@RequestMapping("async")
public class AsyncController {

    Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private MqQueue mqQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 前端会等待 callable返回值。 方法的返回值和callable返回值内容一样。
     * @return
     */
    @GetMapping
    public Callable<String> callable() {
        logger.info("主线程 start");

        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程 start");
                // 业务逻辑
                Thread.sleep(3000);

                logger.info("副线程 end");

                return "callable complete";
            }
        };

        logger.info("主线程 end");

        return result;
    }

    /**
     * 模拟复杂的系统的 下单
     * @return
     */
    @PostMapping
    public DeferredResult<String> createOrder() throws InterruptedException {
        logger.info("主线程 start");

        String orderNum = RandomStringUtils.randomNumeric(16);

        DeferredResult<String> result = new DeferredResult<>();
        // 模拟加入消息队列 和应用二处理
        mqQueue.setStartOrder(orderNum);
        deferredResultHolder.getMap().put(orderNum, result);

        logger.info("主线程 end");
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + "主线程 start");

        Callable<String> a = () -> {
            System.out.println(Thread.currentThread().getId() + "副线程 start");

            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getId() + "副线程 end");

            return "test demo";
        };

        FutureTask futureTask = new FutureTask(a);

        Thread thread = new Thread(futureTask);
        thread.start();
        //thread.join();

        System.out.println(Thread.currentThread().getId() + "主线程 end");
    }
}
