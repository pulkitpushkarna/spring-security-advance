package com.spring_security.controller;

import com.spring_security.entity.Possession;
import com.spring_security.repositories.PossessionRepository;
import com.spring_security.repositories.UserRepository;
import com.spring_security.services.PossessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PossessionService possessionService;

    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }

    @GetMapping("/possession/{possessionId}")
    public ModelAndView getPossession(@PathVariable Long possessionId,Model model){
        ModelAndView modelAndView= new ModelAndView("possession");
        Possession possession = possessionService.findById(possessionId);
        model.addAttribute("possession",possession);
        System.out.println(possession);
        return modelAndView;
    }
}
