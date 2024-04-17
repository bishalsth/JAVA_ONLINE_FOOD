package com.bishal.food.service;

import com.bishal.food.model.Category;
import com.bishal.food.model.Food;
import com.bishal.food.model.Resturant;
import com.bishal.food.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood (CreateFoodRequest req, Resturant resturant, Category category);
    void deleteFood(Long foodId) throws Exception;
    public List<Food> getResturantFood(Long resturantId,
                                       boolean isVegetarian,
                                       boolean available,
                                       boolean isSeasonal,
                                       String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
