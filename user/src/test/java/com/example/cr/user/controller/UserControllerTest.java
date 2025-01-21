package com.example.cr.user.controller;

import com.example.cr.user.entity.User;
import com.example.cr.user.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    /**
     * 在每个测试方法执行之前 , 清空用户数据表
     */
    @BeforeEach
    public void beforeTest(){
        userMapper.deleteByExample(null);
    }

    /**
     * 在每个测试方法执行之后 , 清空用户数据表
     */
    @AfterEach
    public void afterTest(){
        userMapper.deleteByExample(null);
    }

    /**
     * 测试查询用户数量
     * @throws Exception
     */
    @Test
    public void testCount() throws Exception {
        long expected = 10L;
        for (int i = 0; i < expected; i++) {
            User user = new User();
            user.setId(Long.parseLong(""+i));
            user.setMobile("1234567890" + i) ;
            userMapper.insert(user);
        }
        mockMvc.perform(get("/count"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expected)));
    }
}



