package com.akashmjain.BlogApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Controller
public class HelloWorld {
    @GetMapping("/")
    public String helloWorld(Model theModel) {
        theModel.addAttribute("theDate", new Date());
        return "helloworld";
    }
}

