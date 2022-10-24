package com.lickyang.springbootmall.service;

import com.lickyang.springbootmall.dto.UserLoginRequest;
import com.lickyang.springbootmall.dto.UserRegisterRequest;
import com.lickyang.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
