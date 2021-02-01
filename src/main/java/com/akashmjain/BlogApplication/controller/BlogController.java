package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/home")
    public String getPostForFrontPage(Model model) {
        model.addAttribute("posts", blogService.getBlogsForFrontPage());
        return "blog_home";
    }

    @RequestMapping("/show")
    public String getIndividualBlog(@RequestParam int id, Model model) {
        model.addAttribute("post", blogService.getIndividualBlog(id));
        return "show_blog";
    }
}
