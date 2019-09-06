package com.tang.restfuldemo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tang.restfuldemo.validator.CustomValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-20 18:07
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    /**
     * jsonview 实现代码  第一步
     */
    public interface UserSimpleView {};
    public interface UserDetailView extends UserSimpleView{};

    private long id;

    @CustomValidator(message = "自定义用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * jsonview 实现代码 第二步
     * @return
     */
    @JsonView(value = UserSimpleView.class)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonView(value = UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(value = UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
