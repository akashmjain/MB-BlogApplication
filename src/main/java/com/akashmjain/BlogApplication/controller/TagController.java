package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping("/hello")
    public String helloWorld(Model model) {
        model.addAttribute("data", tagService.findAll());
        return "hello_world";
    }
}
