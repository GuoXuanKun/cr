package com.example.cr.user.service;

import com.example.cr.common.exception.UserAlreadyExistsException;
import com.example.cr.user.dto.UserDTO;
import com.example.cr.user.entity.User;
import com.example.cr.user.entity.UserExample;
import com.example.cr.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public long count() {
        return userMapper.countByExample(null);
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    public int register(UserDTO userDTO){

        String mobile = userDTO.getMobile();

        // 校验手机号是否已经注册
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty()) {
            throw new UserAlreadyExistsException("手机号已经注册");
        }

        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setMobile(mobile);
        return userMapper.insert(user);
    }

}
