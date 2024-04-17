package com.bishal.food.request;

import com.bishal.food.model.Category;
import com.bishal.food.model.IngredientItem;
import com.bishal.food.model.Resturant;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CreateFoodRequest {


    private String name;
    private String description;
    private Long price;

    private Category category;

    private List<String> images;

    private Long resturantId;


    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientItem> ingredients;


}
