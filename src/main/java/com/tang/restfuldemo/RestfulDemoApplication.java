package com.tang.restfuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * restful 参考demo
 * 1、mockmvc restful接口测试
 * 2、valid 和 bindingResult 验证 依赖 hibernate
 * 3、时间日期建议以时间戳形式传递，所有前端根据自己需要进行格式化显示
 * 4、jsonview注解 作用在声明出屏蔽相应字段
 *
 */
@SpringBootApplication
public class RestfulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulDemoApplication.class, args);
    }

}
