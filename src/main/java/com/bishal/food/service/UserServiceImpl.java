package com.bishal.food.service;

import com.bishal.food.config.JwtProvider;
import com.bishal.food.model.User;
import com.bishal.food.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findByEmail(email);
        return user;
    }

    @Override
    public User findByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user==null){
            throw new BadCredentialsException("Email not found");
        }
        return user;
    }
}
