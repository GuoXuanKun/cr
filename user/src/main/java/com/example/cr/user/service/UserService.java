package com.example.cr.user.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.cr.common.exception.UserAlreadyExistsException;
import com.example.cr.common.utils.SnowflakeUtil;
import com.example.cr.user.dto.SendCodeDTO;
import com.example.cr.user.dto.UserDTO;
import com.example.cr.user.entity.User;
import com.example.cr.user.entity.UserExample;
import com.example.cr.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public long count() {
        return userMapper.countByExample(null);
    }

    /**
     * 用户注册
     *
     * @param userDTO
     * @return
     */
    public int register(UserDTO userDTO) {

        String mobile = userDTO.getMobile();

        // 校验手机号是否已经注册
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty()) {
            throw new UserAlreadyExistsException("手机号已经注册");
        }

        User user = new User();
        user.setId(SnowflakeUtil.getId());
        user.setMobile(mobile);
        return userMapper.insert(user);
    }

    public void sendCode(SendCodeDTO dto) {

        // 获取到手机号
        String mobile = dto.getMobile();

        // 校验手机号是否已经注册
        UserExample example = new UserExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(example);

        // 如果已经注册，则抛出异常
        if (!users.isEmpty()) {
            throw new UserAlreadyExistsException("手机号已经注册");
        } else { // 如果没有注册，则插入一条数据
            User user = new User();
            user.setId(SnowflakeUtil.getId());
            user.setMobile(mobile);
            userMapper.insert(user);
            log.info("未注册过的手机号, 手机号:{}", mobile);
        }

        // 生成验证码( 4 位数)
        String code = RandomUtil.randomString(4);
        log.info("生成验证码, 验证码:{}, 手机号:{}",code, mobile);

        // 验证码保存到验证码历史记录表
        // 建议的表字段(id , code , mobile , 有效期 , 已使用? , 业务类型【登录/忘记密码...】，生成时间 ， 使用时间)
        log.info("【模拟】验证码已经保存到验证码历史记录, 验证码:{}, 手机号:{}",code, mobile);

        // 验证码保存到验证码历史记录表
        log.info("【模拟】短信发送成功, 验证码:{}, 手机号:{}",code, mobile);


    }
}
