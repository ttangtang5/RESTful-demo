package com.tang.restfuldemo.web.wireMock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Description 通过wireMock 伪造resetFul服务。
 * 1、下载WireMock独立运行程序http://wiremock.org/docs/running-standalone/
 * 2、启动jar
 * 3、编写伪造api代码
 * 还可以通过其他方式生成：https://segmentfault.com/a/1190000012426966
 * @Author tang
 * @Date 2019-08-22 20:35
 * @Version 1.0
 **/
public class WireMockTest {

    public static void main(String[] args) throws IOException {
        WireMock.configureFor(6008);    //告诉程序WireMock的服务端口
        WireMock.removeAllMappings();   //把以前的所有配置清空
        mock("/user/1", "user_01");
    }

    public static void mock(String url, String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("/wireMock/" + fileName + ".json");
        String content = FileUtil.readAsString(resource.getFile());
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(url)).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
