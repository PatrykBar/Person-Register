package com.example.mylittlecompany.patrykbartnicki.PersonRegister.controllers;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RestController
@AllArgsConstructor
public class UserController extends WebMvcConfigurerAdapter {

    private UserService userService;
    private ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserOfApplication userOfApplication){
        if (userService.checkUserForLogin(userOfApplication)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/register")
    public ResponseEntity signUp() throws JsonProcessingException{
        return ResponseEntity.ok(objectMapper.writeValueAsString(userService.getAllUsers()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserOfApplication userOfApplication) {
        if (userService.addUser(userOfApplication)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
