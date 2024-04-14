package com.bishal.food.repo;

import com.bishal.food.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddresRepo extends JpaRepository<Address,Long> {
}
