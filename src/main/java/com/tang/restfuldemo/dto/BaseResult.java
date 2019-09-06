package com.tang.restfuldemo.dto;

import lombok.*;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-20 22:04
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResult {

    private String code;

    private String message;

    private Object data;
}
