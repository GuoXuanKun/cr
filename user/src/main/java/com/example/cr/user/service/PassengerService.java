package com.example.cr.user.service;

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
        Passenger passenger = new Passenger();
        // 给前端没提供的三个字段赋值
        passenger.setId(SnowflakeUtil.getId());
        Date time = new Date();
        passenger.setCreateTime(time);
        passenger.setUpdateTime(time);
        // 给前端提供的字段赋值
        passenger.setUserId(request.getUserId());
        passenger.setName(request.getName());
        passenger.setIdCard(request.getIdCard());
        passenger.setType(request.getType());

        passengerMapper.insert(passenger);
    }
}
