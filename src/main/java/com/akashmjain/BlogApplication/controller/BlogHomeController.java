package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BlogHomeController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/")
    public String getFrontPageData(@RequestParam(name = "page", required = false, defaultValue = "0") int pageNo, @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,Model model) {
        Pageable page = PageRequest.of(pageNo, limit);
        Page<PostEntity> pageWithPosts = postService.findPages(page);
        model.addAttribute("totalPages",pageWithPosts.getTotalPages());
        model.addAttribute("posts", pageWithPosts.getContent());
        return "index";
    }

    @RequestMapping("/blog")
    public String getIndividualBlog(@RequestParam("id") int blogId, Model model) {
        model.addAttribute("blog", postService.findById(blogId));
        model.addAttribute("commentEntity", new CommentEntity());
        return "show_blog";
    }
}

