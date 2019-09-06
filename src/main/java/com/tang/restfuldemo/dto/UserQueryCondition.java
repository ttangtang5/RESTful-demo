package com.tang.restfuldemo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-20 18:56
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "用户条件查询对象")
public class UserQueryCondition {

    @ApiModelProperty(value = "姓名模糊查询")
    private String usernameLike;

    private String password;
}
