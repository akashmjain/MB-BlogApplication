package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogHomeController {
    BlogService blogService;

    @Autowired
    public BlogHomeController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/")
    public String getFrontPageData(Model model) {
        model.addAttribute("posts", blogService.getBlogPostsForFrontPage());
        return "index";
    }

    @RequestMapping("/blog")
    public String getIndividualBlog(@RequestParam("id") int blogId, Model model) {
        model.addAttribute("blog", blogService.getIndividualBlogPost(blogId));
        return "show_blog";
    }
}
