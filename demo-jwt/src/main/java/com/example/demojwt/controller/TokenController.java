package com.example.demojwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demojwt.util.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SYA
 * @Date 2023/4/14 10:55
 * @Description:
 */
@RestController
public class TokenController {


    /**
     * 登录
     *
     * @param userName 用户名
     * @param passWord 通过单词
     * @return {@link JSONObject}
     */
    @PostMapping("/login")
    public JSONObject login(@RequestParam("userName") String userName,
                            @RequestParam("passWord") String passWord) {

        /**
         *
         * ========
         * 验证账户密码错误
         */

        JSONObject json = new JSONObject();
        String token = JwtUtil.createToken(userName);
        if (StringUtils.hasText(token)) {
            json.put("token", token);
        }
        return json;
    }

    /**
     * 需要 Token 验证的接口
     */
    @PostMapping("/info")
    public JSONObject info() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);

        String userName = JwtUtil.getUserName();
        jsonObject.put("userName", userName);

        return jsonObject;
    }

}
