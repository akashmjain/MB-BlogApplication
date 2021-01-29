package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.User;
import com.akashmjain.BlogApplication.service.UserService;
import com.akashmjain.BlogApplication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String findAll(Model model) {
         List<User> users = userService.findAll();
         model.addAttribute("users", users);
         return "show_users";
    }


    /*
    @GetMapping("/")
    public String helloWorld(Model theModel) {
        theModel.addAttribute("theDate", new Date());
        return "helloworld";
    }*/
}


