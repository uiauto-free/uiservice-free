package com.uiautofree.auth.dao;

import com.uiautofree.auth.domain.UserBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    List<UserBase> findAllUsers();

    UserBase getByUserName(@Param("userName") String userName);

    UserBase getUserById(@Param("id") Long id);
}
