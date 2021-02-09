package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;

@Controller
@RequestMapping("/admin")
public class AdminPostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    private final int EXCERPT_SIZE = 100;

    /* CREATE SECTION */
    @RequestMapping("/post/create")
    public String createPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("postEntity", postEntity);
        model.addAttribute("tagStringData", "");
        model.addAttribute("users", userService.findAll());
        return "write_blog";
    }

    /* READ SINGLE POST */
    @RequestMapping("/post/read")
    public String readPost(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("postEntity", postService.findById(postId));
        return "show_blog";
    }

    /* UPDATE SECTION */
    @RequestMapping("/post/update")
    public String updatePost(@RequestParam("postId") int postId, Model model) {
        PostEntity postEntity = postService.findById(postId);
        model.addAttribute("postEntity", postEntity);
        return "update_blog";
    }

    /* DELETE POST */
    @RequestMapping("/post/delete")
    public String deletePost(@RequestParam("postId")int postId) {
        postService.deleteById(postId);
        return "redirect:/";
    }

    /* Calling */
    @RequestMapping("/post/create/save")
    public String saveNewPost(@ModelAttribute("postEntity") PostEntity postEntity, @RequestParam("tagStringData") String tagString, @RequestParam("authorId") int authorId) {
        String content = postEntity.getContent();
        String excerpt = content.length() > EXCERPT_SIZE ? content.substring(0, EXCERPT_SIZE) : content;
        postEntity.setAuthor(userService.findById(authorId));
        postEntity.setContent(content);
        postEntity.setExcerpt(excerpt);
        postEntity.setTags(tagService.stringToTag(tagString));
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setIsPublished(true);
        postService.save(postEntity);
        return "redirect:/";
    }

    @RequestMapping("/post/update/save")
    public String saveUpdatedPost(@ModelAttribute("postEntity") PostEntity postEntity) {
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postService.save(postEntity);
        return "redirect:/admin/post/read?postId="+postEntity.getId();
    }
}

