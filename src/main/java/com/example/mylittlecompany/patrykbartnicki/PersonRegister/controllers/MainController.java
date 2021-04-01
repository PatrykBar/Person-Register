package com.example.mylittlecompany.patrykbartnicki.PersonRegister.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"", "/", "/index"})
    public String mainPage(){
        return "index";
    }

}
