package com.bishal.food.service;

import com.bishal.food.model.Category;
import com.bishal.food.model.Food;
import com.bishal.food.model.Resturant;
import com.bishal.food.repo.FoodRepo;
import com.bishal.food.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{


    private final FoodRepo foodRepo;

    public FoodServiceImpl(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    @Override
    public Food createFood(CreateFoodRequest req, Resturant resturant, Category category) {
        Food food = new Food();
        food.setResturant(resturant);
        food.setCategory(category);
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());

        Food savedFood = foodRepo.save(food);
        resturant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setResturant(null);
        foodRepo.save(food);

    }

    @Override
    public List<Food> getResturantFood(Long resturantId, boolean isVegetarian, boolean available, boolean isSeasonal, String foodCategory) {

        List<Food> foods = foodRepo.findByResturantId(resturantId);

        if(isVegetarian){
            foods= filterByVegetaian(foods,isVegetarian);

        }
        if(isSeasonal){
            foods = filterbySeasonal(foods,isSeasonal);

        }
        if(available){
            foods = filterByAvailable(foods,available);

        }
        if(foodCategory!=null && !foodCategory.equals("")){

            foods = filterByFoodCategory(foods,foodCategory);

        }

        return foods;
    }

    private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getCategory()!=null){
//                foodCategory=momo and list all the types of momos
                return food.getCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterByAvailable(List<Food> foods, boolean available) {

        return foods.stream().filter(food -> food.isAvailable()==available).collect(Collectors.toList());
    }

    private List<Food> filterbySeasonal(List<Food> foods, boolean isSeasonal) {

        return foods.stream().filter(food->food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByVegetaian(List<Food> foods, boolean isVegetarian) {

        return foods.stream().filter(food->food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }



    @Override
    public List<Food> searchFood(String keyword) {

        return foodRepo.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepo.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("Food not exist");
        }
        return optionalFood.get();

    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepo.save(food);
    }
}
