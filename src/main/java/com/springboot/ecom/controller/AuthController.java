package com.springboot.ecom.controller;

import com.springboot.ecom.dto.AdminDto;
import com.springboot.ecom.dto.response.TokenDto;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.utility.JwtUtility;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtility jwtUtility;
    @PostMapping("/api/admin/add")
    public void addAdmin(@RequestBody AdminDto adminDto){
        userService.addAdmin(adminDto);
    }

    // Before the user hits this /login api here in controller, spring would have already checked credentials
    @GetMapping("/login")
    public TokenDto login(Principal principal){

        String loggedInUsername = principal.getName();
        // Generate the token for this username
         Logger logger=LoggerFactory.getLogger("AuthController.class");
         logger.info("Login User Details: {}",loggedInUsername);

        String token = jwtUtility.generateToken(loggedInUsername);
        logger.info("Logged in with Token : {}",token);
        // fetch user details to pass the role
        User user =  userService.getUserDetails(loggedInUsername);
        logger.info("User Details : {}",user.toString());
        logger.info("JWT Token Expiration date  :{} ",jwtUtility.extractExpiration(token).toString());

        return new TokenDto(
                token,
                jwtUtility.extractExpiration(token).toString(),
                user.getRole().toString()
        );
    }
}
