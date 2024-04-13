package com.bishal.food.repo;

import com.bishal.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    public  User findByEmail(String username);
}
