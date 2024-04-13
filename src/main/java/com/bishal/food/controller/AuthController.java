package com.bishal.food.controller;

import com.bishal.food.config.JwtProvider;
import com.bishal.food.model.Cart;
import com.bishal.food.model.USER_ROLE;
import com.bishal.food.model.User;
import com.bishal.food.repo.CartRepo;
import com.bishal.food.repo.UserRepo;
import com.bishal.food.request.LoginRequest;
import com.bishal.food.response.AuthResponse;
import com.bishal.food.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepo.findByEmail(user.getEmail());
        if(isEmailExist !=null){
            throw new Exception("Email is already used please enter another email");
        }

        User createdUser = new User();
        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());
//        createdUser.setAddresses(user.getAddresses());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(createdUser);

         Cart  cart = new Cart();
         cart.setCustomer(savedUser);
         cartRepo.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Successfully account created");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> handleUserLogin(@RequestBody LoginRequest req){

        String username=req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username,password);

//        getting role
        Collection<? extends GrantedAuthority> authorities =authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Success login");
        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse,HttpStatus.OK);


    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid passowrd");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}