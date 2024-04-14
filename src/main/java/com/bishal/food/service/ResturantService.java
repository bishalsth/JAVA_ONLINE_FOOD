package com.bishal.food.service;

import com.bishal.food.dto.ResturantDto;
import com.bishal.food.model.Resturant;
import com.bishal.food.model.User;
import com.bishal.food.request.CreateResturantRequest;

import java.util.List;

public interface ResturantService {

    public Resturant createResturant(CreateResturantRequest req, User user);
    public Resturant updateResturant(Long resturantId, CreateResturantRequest updateResturant) throws Exception;
    public void deleteResturant (Long resturantId) throws Exception;
    public List<Resturant> getAllResturant();
    public List<Resturant> searchResturant(String keyword);
    public Resturant findResturantById(Long id) throws Exception;
    public Resturant getResturantByUserId(Long userId) throws Exception;
    public ResturantDto addToFavorites(Long resturantId,User user) throws Exception;
    public Resturant updateResturantStatus(Long id) throws Exception;
}
