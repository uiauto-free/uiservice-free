package com.uiautofree.controller.auth;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uiautofree.auth.domain.UserBase;
import com.uiautofree.auth.service.UserService;
import com.uiautofree.common.config.global.BTResponse;
import com.uiautofree.common.config.jwtoken.JwtokenHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * 本項目只是简单实现了 auth 相关操作
     *
     * @return
     */
    @GetMapping("/user/all")
    @ResponseBody
    public BTResponse<JSONArray> getAllUsers() {
        BTResponse<JSONArray> res = new BTResponse<>();
        List<UserBase> users = userService.getAllUsers();
        JSONArray data = new JSONArray();
        users.forEach(user -> {
            JSONObject result = JSON.parseObject(JSON.toJSONString(user));
            data.add(result);
        });
        res.setCode(200);
        res.setData(data);
        return res;
    }

    @GetMapping("/user/get")
    @ResponseBody
    public BTResponse<JSONObject> getUserById(HttpServletRequest request, @RequestParam(required = false) Long id) {
        BTResponse<JSONObject> res = new BTResponse<>();
        if(id == null) {
            Object loginUserId = request.getAttribute("loginUserId");
            if (loginUserId != null) {
                id = Long.valueOf(loginUserId.toString());
            }
        }
        UserBase user = userService.getUserById(id);
        JSONObject data = new JSONObject();
        data.put("id", user.getId());
        data.put("userId", user.getUserId());
        data.put("userCode", user.getUserCode());
        data.put("userName", user.getUserName());
        data.put("userNickName", user.getUserNickName());
        res.setCode(200);
        res.setData(data);
        return res;
    }

    /**
     * 登录
     *
     * @param req
     * @param res
     * @param data
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public BTResponse<JSONObject> login(HttpServletRequest req, HttpServletResponse res, @RequestBody JSONObject data) {
        BTResponse<JSONObject> response = new BTResponse<>();
        JSONObject body = new JSONObject();
        body.put("result", "need login");
        if (data.containsKey("userName") && data.containsKey("password")) {
            body = userService.login(data);
            body.put("data", true);
            body.put("token", JwtokenHelper.createToken(body));
        } else {
            throw new RuntimeException("userName or password missing!");
        }
        response.setData(body);
        response.setCode(200);
        return response;
    }


}
