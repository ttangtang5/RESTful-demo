package com.tang.restfuldemo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tang.restfuldemo.dto.UserDto;
import com.tang.restfuldemo.dto.UserQueryCondition;
import com.tang.restfuldemo.exceptions.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Supplier;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-20 17:44
 * @Version 1.0
 **/
@Api(value = "用户API")
@RestController
@RequestMapping("user")
public class UserController {

    @ApiOperation(value = "查询用户列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @JsonView(UserDto.UserSimpleView.class) //Jsonview 实现代码 第三步
    public List<UserDto> queryUser(UserQueryCondition userQueryCondition) {
        System.out.println("userQueryCondition.toString() = " + userQueryCondition.toString());
        
        List users = new ArrayList();

        Supplier<UserDto> userSupplier = UserDto::new;
        UserDto userDto1 = userSupplier.get();
        UserDto userDto2 = userSupplier.get();
        UserDto userDto3 = userSupplier.get();

        // 不建议
        users = Arrays.asList(userDto1, userDto2, userDto3);

        return users;
    }

    /**
     * /{id:\\d+} 表示id的正则表达式
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/{id:\\d+}")
    @JsonView(UserDto.UserDetailView.class)
    public UserDto getUserInfo(@ApiParam(value = "用户编号", required = true) @PathVariable long id) {
        UserDto userDto = new UserDto();

        userDto.setUsername("tang");

        return userDto;
    }

    /**
     * 只是用valid 验证失败不会进入方法体内。
     * bindingResult对象用于接收valid验证失败的信息。使用它之后即使验证失败也可以进入方法体
     * @param userDto
     * @param errors
     * @return
     */
    @PostMapping
    public UserDto saveUserInfo(@Valid @RequestBody UserDto userDto, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                System.out.println(fieldError.getField() +" = " + fieldError.getDefaultMessage());
            });

            return null;
        }

        System.out.println("userDto = " + userDto.toString());

        userDto.setId(1);

        return userDto;
    }

    @PutMapping(value = "/{id:\\d+}")
    public int updateUser(@RequestBody UserDto userDto) {
        System.out.println("userDto.toString() = " + userDto.toString());

        throw new CustomException("自定义异常");

        //return 1;
    }

    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteUser(@PathVariable long id) {
        System.out.println("id = " + id);
    }
}
