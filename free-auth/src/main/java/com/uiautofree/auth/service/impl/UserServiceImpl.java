package com.uiautofree.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uiautofree.auth.dao.UserDao;
import com.uiautofree.auth.domain.UserBase;
import com.uiautofree.auth.service.UserService;
import com.uiautofree.common.config.jwtoken.JwtokenHelper;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserBase> getAllUsers() {
        log.error("123 {}", userDao);
        return userDao.findAllUsers();
    }

    @Override
    public UserBase getUserByUserName(String userName) {
        try {
            UserBase base = userDao.getByUserName(userName);
            return base;
        } catch (Exception e) {
            throw new RuntimeException("获取错误！");
        }
    }

    @Override
    public UserBase getUserById(Long id) {  try {
        UserBase base = userDao.getUserById(id);
        return base;
    } catch (Exception e) {
        throw new RuntimeException("获取错误！");
    }
    }

    @Override
    public JSONObject login(JSONObject data) {
        JSONObject result = new JSONObject();
        String userName = data.getString("userName");
        String password = data.getString("password");
        UserBase userBase = getUserByUserName(userName);
        if (userBase == null) {
            throw new RuntimeException("用户不存在，登录失败!");
        }
        if (userBase.getUserName().equals(userName) && userBase.getPassword().equals(password)) {
            return JSON.parseObject(JSON.toJSONString(userBase));
        } else {
            result.put("data", false);
            result.put("errMsg", "user name or password error");
        }
        return JSON.parseObject(JSON.toJSONString(userBase));
    }
}
