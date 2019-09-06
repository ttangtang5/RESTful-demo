package com.tang.restfuldemo;

import com.alibaba.fastjson.JSON;
import com.tang.restfuldemo.dto.UserDto;
import com.tang.restfuldemo.dto.UserQueryCondition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-20 17:45
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulDemoApplication.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void startUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void query() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .param("usernameLike", "tang")
                .param("password", "tang")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                // jsonPath 参考https://github.com/json-path/JsonPath
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn();

        // 被jsonview修饰后 部分字段被屏蔽
        System.out.println("mvcResult = " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getUserInfo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tang"))
                .andReturn();

        System.out.println("mvcResult = " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(UserDto.builder().username("tang").password("tang").build()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void createUserFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(UserDto.builder().username("").password("").build()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(UserDto.builder().id(1).username("tang").password("tang").build())))
                //.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andReturn();

        System.out.println("mvcResult = " + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
