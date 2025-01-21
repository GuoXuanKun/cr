package com.example.cr.user.service;

import com.example.cr.user.entity.User;
import com.example.cr.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public long count() {
        return userMapper.countByExample(null);
    }

    /**
     * 用户注册
     * @param mobile
     * @return
     */
    public int register(String mobile){
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setMobile(mobile);
        return userMapper.insert(user);
    }

}
