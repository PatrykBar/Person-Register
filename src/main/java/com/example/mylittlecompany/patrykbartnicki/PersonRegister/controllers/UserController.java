package com.example.mylittlecompany.patrykbartnicki.PersonRegister.controllers;

import com.example.mylittlecompany.patrykbartnicki.PersonRegister.models.UserOfApplication;
import com.example.mylittlecompany.patrykbartnicki.PersonRegister.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;


//    @GetMapping("/hy") //For REST
//    @ResponseBody
//    public String hy(){
//        return "hy";
//    }

    @GetMapping("/hello")
    public String forUser(Principal principal, Model model){
        model.addAttribute("name", principal.getName());
        return "hello";
    }

    @GetMapping("/sign_up")
    public String signUp(Model model){
        model.addAttribute("user", new UserOfApplication());
        return "sign_up";
    }

    @PostMapping("/register")
    public String register(UserOfApplication userOfApplication){
        userService.addUser(userOfApplication);
        return "index";
    }
}
