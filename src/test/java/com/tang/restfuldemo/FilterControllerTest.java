package com.tang.restfuldemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-21 22:43
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestfulDemoApplication.class})
public class FilterControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void startUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void upload() throws Exception {
        MvcResult file = mockMvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                // 参数一：参数名，参数二：原文件名 参数三：contentType 参数四：文件内容
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes()))
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        System.out.println("file = " + file.getResponse().getContentAsString());
    }

}
