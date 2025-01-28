package com.example.cr.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CustomJWTUtils {
    private static final Logger log = LoggerFactory.getLogger(CustomJWTUtils.class);
    // 生产环境下应该写到配置文件, 配置文件该被忽略
    public static final String key = "CRJwtKeySbSBsb678";

    public static String createToken(Long id, String mobile) {
        Map<String, Object> payload = new HashMap<>();

        // 业务内容
        payload.put("id", id);
        payload.put("mobile", mobile);

        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);
        // 额外的几个时间值
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        payload.put(JWTPayload.NOT_BEFORE, now);

        String token = JWTUtil.createToken(payload, key.getBytes());
        log.info("生成 JWT token = {}, id = {}, mobile = {}", token, id, mobile);

        return token;
    }

    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        boolean validate = jwt.validate(0);
        log.info("JWT token 校验结果 = {}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();

        // 返回给前端之前，删除额外增加的几个时间值
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);

        log.info("从 JWT token 中获取的原始内容 = {}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        createToken(1876605648607776768L, "18912345678");

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MzYyNTI5NTAsIm1vYmlsZSI6IjE4OTEyMzQ1Njc4IiwiaWQiOjE4NzY2MDU2NDg2MDc3NzY3NjgsImV4cCI6MTczNjI1Mjk2MCwiaWF0IjoxNzM2MjUyOTUwfQ.OmcO19K_RR7hyAuP-KIsKF7KTZrob07b3viPkI5R6v8";
        validate(token);

        getJSONObject(token);
    }
}
