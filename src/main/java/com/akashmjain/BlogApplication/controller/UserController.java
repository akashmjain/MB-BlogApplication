package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.User;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable int userId, Model model) {
        User user = userService.findById(userId);
        if(user == null) {
            System.out.println("user not found");
        }
        model.addAttribute("users",user);
        return "show_users";
    }

}


