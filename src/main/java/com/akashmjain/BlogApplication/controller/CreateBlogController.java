package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

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
        model.addAttribute("tagStringData", "");
        return "write_blog";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") PostEntity post, @RequestParam("tag_string_data") String tags) {
        //@TODO parse the tags and craete new tags if needed and assign those to Post;
        post.setAuthor(userService.findById(1));
        post.setPublished(true);
        post.setPublished(true);
        post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        post.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postService.save(post);
        return "redirect:/";
    }
    /*
    private List<TagEntity> createTags(String tagLine) {
        String [] tagArr = tagLine.split(" ");
        return null;
    }*/

    @RequestMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") PostEntity post) {
        postService.save(post);
        return "redirect:/blog?id="+post.getId();
    }

}
