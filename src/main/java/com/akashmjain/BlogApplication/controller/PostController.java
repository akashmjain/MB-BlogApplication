package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "hello_world";
    }
}
