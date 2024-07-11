package com.allogica.ForumHUBChallenge.Controller;

import com.allogica.ForumHUBChallenge.Model.Entities.DTOs.UserDTO;
import com.allogica.ForumHUBChallenge.Model.Entities.User;
import com.allogica.ForumHUBChallenge.Model.Services.Security.TokenService;
import com.allogica.ForumHUBChallenge.Model.Services.UserService;
import com.allogica.ForumHUBChallenge.Security.JWTTokenData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.userName(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var JWTToken = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JWTTokenData(JWTToken));
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody @Valid UserDTO data) {
        System.out.println("Creating user");
        userService.createUser(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}