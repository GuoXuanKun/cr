package com.example.cr.user.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.cr.common.exception.CommonBusinessException;
import com.example.cr.common.exception.UserAlreadyExistsException;
import com.example.cr.common.exception.UserNotExistsException;
import com.example.cr.common.utils.SnowflakeUtil;
import com.example.cr.user.dto.LoginDTO;
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
//        String code = RandomUtil.randomString(4);
        String code = "6666";
        log.info("生成验证码, 验证码:{}, 手机号:{}",code, mobile);

        // 验证码保存到验证码历史记录表
        // 建议的表字段(id , code , mobile , 有效期 , 已使用? , 业务类型【登录/忘记密码...】，生成时间 ， 使用时间)
        log.info("【模拟】验证码已经保存到验证码历史记录, 验证码:{}, 手机号:{}",code, mobile);

        // 验证码保存到验证码历史记录表
        log.info("【模拟】短信发送成功, 验证码:{}, 手机号:{}",code, mobile);


    }

    /*
        暂时先直接返回 User，但是你得知道不应该把用户所有的信息全部返回出去，因为：
        1. 会涉及隐私的问题，比如密码字段
        2. 会涉及到很多不必要的字段，比如数据库中的创建时间、更新时间、额外标记字段等等
        3. 还会涉及到有些字段并不是 User 这个实体的字段，比如 token 等

        以上问题的解决方法类似之前引入的 DTO，除了 DTO 可能还会遇到 VO、BO、PO、DO、Entity、POJO 等各种词汇
            参考：https://www.cnblogs.com/east7/p/15057400.html
            这篇文章说的也不是标准答案，国内外描述也不一定一致

            为了避免记忆一大堆名词，除了 Entity，我们只用两个额外的概念去封装系统的需求：
            1. request 封装从「前端」发送到「后端」的数据
            2. response 封装从「后端」返回给「前端」的数据

         */
    public User login(LoginDTO dto) {

        String mobile = dto.getMobile();
        String code = dto.getCode();

        UserExample userExample = new UserExample();
        userExample.createCriteria().andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(userExample);

        // 校验用户是否存在
        if (users.isEmpty()) {
            throw new UserNotExistsException("用户不存在");
        }

        // 校验验证码是否正确
        if (!"6666".equals(code)) {
            throw new CommonBusinessException("验证码错误");
        }

        return users.get(0);
    }
}
