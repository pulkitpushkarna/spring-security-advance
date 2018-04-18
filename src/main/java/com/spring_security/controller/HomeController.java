package com.spring_security.controller;


import com.spring_security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }
}
