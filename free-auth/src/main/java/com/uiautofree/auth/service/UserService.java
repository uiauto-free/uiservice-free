package com.uiautofree.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.uiautofree.auth.domain.UserBase;

import java.util.List;

public interface UserService {
    List<UserBase> getAllUsers();

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    UserBase getUserByUserName(String userName);

    /**
     * 获取 用户数据 id
     * @param id
     * @return
     */
    UserBase getUserById(Long id);

    /**
     * 登录
     * @param data
     * @return
     */
    JSONObject login(JSONObject data);
}
