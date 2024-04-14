package com.bishal.food.controller;

import com.bishal.food.model.Resturant;
import com.bishal.food.model.User;
import com.bishal.food.request.CreateResturantRequest;
import com.bishal.food.response.MessageResponse;
import com.bishal.food.service.ResturantService;
import com.bishal.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/resturants")
public class AdminResturantController {

    @Autowired
    private ResturantService resturantService;

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<Resturant> createResturant(
            @RequestBody CreateResturantRequest   req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findByJwtToken(jwt);
        Resturant resturant = resturantService.createResturant(req, user);
        return new ResponseEntity<>(resturant, HttpStatus.CREATED);

    }


    @PutMapping("/{resturantId}/update")
        public ResponseEntity<Resturant> updateResturant(
                @RequestBody CreateResturantRequest req,
                @RequestHeader("Authorization")String jwt,
                @PathVariable Long resturantId
        ) throws Exception {
            Resturant resturant = resturantService.updateResturant(resturantId, req);
            return new ResponseEntity<>(resturant,HttpStatus.OK);

        }



        @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> deletResturant(@PathVariable Long id,
                                                          @RequestHeader("Authorization")String jwt) throws Exception {
        resturantService.deleteResturant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Resturant deleted successfully");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);

        }

        @PutMapping("/{id}/status")
    public ResponseEntity<Resturant> udpateResturantStatus(@PathVariable Long id) throws Exception {
            Resturant resturant = resturantService.updateResturantStatus(id);
            return new ResponseEntity<>(resturant,HttpStatus.OK);

        }

        @GetMapping("/user")
    public ResponseEntity<Resturant> findResturantByUserId( @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findByJwtToken(jwt);
            Resturant resturant = resturantService.getResturantByUserId(user.getId());
            return new ResponseEntity<>(resturant,HttpStatus.OK);
        }


}
