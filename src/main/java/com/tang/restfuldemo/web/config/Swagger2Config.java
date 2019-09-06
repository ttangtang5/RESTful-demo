package com.tang.restfuldemo.web.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description swagger 配置文档
 *  *  @Api： 描述 Controller
 *  *  @ApiIgnore： 忽略该 Controller，指不对当前类做扫描
 *  *  @ApiOperation： 描述 Controller类中的 method接口
 *  *  @ApiParam： 单个参数描述，与 @ApiImplicitParam不同的是，他是写在参数左侧的。如（ @ApiParam(name="username",value="用户名")Stringusername）
 *  *  @ApiModel： 描述 POJO对象
 *  *  @ApiProperty： 描述 POJO对象中的属性值
 *  *  @ApiImplicitParam： 描述单个入参信息
 *  *  @ApiImplicitParams： 描述多个入参信息
 *  *  @ApiResponse： 描述单个出参信息
 *  *  @ApiResponses： 描述多个出参信息
 *  *  @ApiError： 接口错误所返回的信息
 * @Author tang
 * @Date 2019-08-22 18:36
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 扫描带@Api注解的接口类 RequestHandlerSelectors.withClassAnnotation(Api.class)
                .apis(RequestHandlerSelectors.basePackage("com.tang.restfuldemo.web"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxx接口文档")
                .description("xxx相关接口的文档")
                .termsOfServiceUrl("http://www.xxx.com")
                .version("1.0")
                .build();
    }
}
