package com.example.cr.user.controller;

import com.example.cr.user.dto.UserDTO;
import com.example.cr.user.entity.User;
import com.example.cr.user.mapper.UserMapper;
import com.example.cr.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

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
//                .andExpect(content().string(String.valueOf(expected)));
                .andExpect(jsonPath("$.data").value(expected));
    }

    @Test
    void sendCode_shouldSendVerificationCode() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        String mobile = "18812345678";
        userDTO.setMobile(mobile);
        String content = objectMapper.writeValueAsString(userDTO);

        // Act & Assert
        mockMvc.perform(post("/send-code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "zh-CN")
                        .content(content))
                /*
                {
                  "code": 200,
                  "msg": "OK",
                  "data": null
                }
                 */
                //.andDo(print())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty())
        ;

        // 根据之前的配置，单元测试执行之前都会清空数据库
        // 所以每次发送验证码的时候，都属于新手机号，会往用户表插入数据
        // 额外验证数据库真的被插入了
        long userCount = userService.count();
        Assertions.assertEquals(1, userCount);

        User registeredUser = userMapper.selectByExample(null).get(0);
        Assertions.assertEquals(mobile, registeredUser.getMobile());
    }

}



