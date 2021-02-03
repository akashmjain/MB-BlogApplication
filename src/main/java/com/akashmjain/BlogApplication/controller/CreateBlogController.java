package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/create")
public class CreateBlogController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @RequestMapping("/newpost")
    public String createNewPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("post", postEntity);
        return "write_blog";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") PostEntity post) {
        post.setAuthor(userService.findById(1));
        post.setPublished(true);
        post.setExcerpt("basic excerpt");
        postService.save(post);
        return "redirect:/";
    }

    @RequestMapping("/updatePost")
    public String updatePost() {
        return null;
    }
}
