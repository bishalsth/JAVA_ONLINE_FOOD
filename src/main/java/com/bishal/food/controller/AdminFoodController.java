package com.bishal.food.controller;

import com.bishal.food.model.Food;
import com.bishal.food.model.Resturant;
import com.bishal.food.request.CreateFoodRequest;
import com.bishal.food.response.MessageResponse;
import com.bishal.food.service.FoodService;
import com.bishal.food.service.ResturantService;
import com.bishal.food.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private final UserService userService;
    private final ResturantService resturantService;
    private final FoodService  foodService;



    public AdminFoodController(UserService userService, ResturantService resturantService, FoodService foodService) {
        this.userService = userService;
        this.resturantService = resturantService;
        this.foodService = foodService;
    }


    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {


        Resturant resturant = resturantService.findResturantById(req.getResturantId());
        Food food = foodService.createFood(req, resturant, req.getCategory());
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
        foodService.deleteFood(id);
        MessageResponse response = new MessageResponse();
        response.setMessage("Food deleted Succesfully");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PutMapping("/{id}/udpate-Availability")
    public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id) throws Exception {
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }


}
