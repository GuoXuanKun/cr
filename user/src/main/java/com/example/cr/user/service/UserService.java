package com.example.cr.user.service;

import com.example.cr.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public long count() {
        return userMapper.count();
    }

}
