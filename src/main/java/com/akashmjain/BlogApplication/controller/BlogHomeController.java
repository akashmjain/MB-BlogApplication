package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogHomeController {
    BlogService blogService;

    @Autowired
    public BlogHomeController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/")
    public String getFrontPageData(@RequestParam(name = "page", required = false, defaultValue = "0") int pageNo, @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,Model model) {
        Page<PostEntity> page = blogService.getBlogPostsForFrontPage(pageNo, limit, model);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("posts", page.getContent());
        return "index";
    }

    @RequestMapping("/blog")
    public String getIndividualBlog(@RequestParam("id") int blogId, Model model) {
        model.addAttribute("blog", blogService.getIndividualBlogPost(blogId));
        return "show_blog";
    }
   
}

