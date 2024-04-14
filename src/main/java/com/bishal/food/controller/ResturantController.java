package com.bishal.food.controller;

import com.bishal.food.dto.ResturantDto;
import com.bishal.food.model.Resturant;
import com.bishal.food.model.User;
import com.bishal.food.service.ResturantService;
import com.bishal.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resturants")
public class ResturantController {
    @Autowired
    private ResturantService resturantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Resturant>> searchResturant(@RequestParam String keyword){
        List<Resturant> resturants = resturantService.searchResturant(keyword);
        return new ResponseEntity<>(resturants, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resturant>> getAllResturant(){
        List<Resturant> allResturant = resturantService.getAllResturant();
        return new ResponseEntity<>(allResturant,HttpStatus.OK);
    }

    @GetMapping("/{id}/resturant")
    public ResponseEntity<Resturant > getResutrantById(@PathVariable Long id,
                                                       @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findByJwtToken(jwt);
        Resturant resturant = resturantService.findResturantById(id);
        return new ResponseEntity<>(resturant,HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<ResturantDto> addTpFavprites(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findByJwtToken(jwt);
        ResturantDto dto = resturantService.addToFavorites(id, user);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }



}
