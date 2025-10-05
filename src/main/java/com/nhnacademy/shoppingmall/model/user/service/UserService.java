package com.nhnacademy.shoppingmall.model.user.service;

import com.nhnacademy.shoppingmall.model.user.domain.User;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

}
