package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    private PostService postService;

    @Autowired
    public TestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping("/")
    public String helloWorld(Model model) {
        List<PostEntity> lists = postService.findAll();
        model.addAttribute("data", lists);
        return "hello_world";
    }

}
