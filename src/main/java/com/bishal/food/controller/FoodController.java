package com.bishal.food.controller;

import com.bishal.food.model.Food;
import com.bishal.food.service.FoodService;
import com.bishal.food.service.ResturantService;
import com.bishal.food.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final UserService userService;
    private final ResturantService resturantService;
    private final FoodService foodService;



    public FoodController(UserService userService, ResturantService resturantService, FoodService foodService) {
        this.userService = userService;
        this.resturantService = resturantService;
        this.foodService = foodService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword){
        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/resturant/{resturantId}")
    public ResponseEntity<List<Food>> getResturantFood(@RequestParam boolean isVegetarian,
                                                 @RequestParam boolean available,
                                                 @RequestParam boolean isSeasonal,
                                                 @RequestParam(required = false)String foodCategory,
                                                 @PathVariable Long resturantId){

        List<Food> resturantFoods = foodService.getResturantFood(resturantId, isVegetarian, available, isSeasonal, foodCategory);

        return new ResponseEntity<>(resturantFoods,HttpStatus.OK);

    }
}
