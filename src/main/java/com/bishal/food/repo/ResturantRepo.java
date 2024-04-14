package com.bishal.food.repo;

import com.bishal.food.model.Resturant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResturantRepo extends JpaRepository<Resturant,Long> {



    @Query("SELECT r from Resturant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%' )) "+
    "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%') )  "
    )
    List<Resturant> findBySearchQuery(String query);
    Resturant findByOwnerId(Long id);
}
