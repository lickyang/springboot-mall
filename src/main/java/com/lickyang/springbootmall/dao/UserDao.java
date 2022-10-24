package com.lickyang.springbootmall.dao;

import com.lickyang.springbootmall.dto.UserRegisterRequest;
import com.lickyang.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
