package com.bishal.food.service;

import com.bishal.food.model.User;

public interface UserService {

    public User findByJwtToken(String jwt) throws Exception;
    public User findByEmail(String email) throws Exception;
}
