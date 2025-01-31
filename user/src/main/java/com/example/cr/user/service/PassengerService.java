package com.example.cr.user.service;

import cn.hutool.core.bean.BeanUtil;
import com.example.cr.common.util.SnowflakeUtil;
import com.example.cr.user.entity.Passenger;
import com.example.cr.user.mapper.PassengerMapper;
import com.example.cr.user.request.PassengerRequest;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PassengerService {
    @Autowired
    private PassengerMapper passengerMapper;
    public void save(PassengerRequest request) {
        Passenger passenger = BeanUtil.copyProperties(request, Passenger.class);
        // 给前端没提供的三个字段赋值
        passenger.setId(SnowflakeUtil.getId());
        Date time = new Date();
        passenger.setCreateTime(time);
        passenger.setUpdateTime(time);
        // 以及前端没提供的 userId 字段
//        passenger.setUserId(当前登录用户的 userId);

        passengerMapper.insert(passenger);
    }
}
