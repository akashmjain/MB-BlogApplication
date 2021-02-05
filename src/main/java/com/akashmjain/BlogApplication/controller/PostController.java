package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;


@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;

    /* CREATE SECTION */
    @RequestMapping("/post/create")
    public String createPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("postEntity", postEntity);
        return "write_blog";
    }

    /* READ SECTION */
    @RequestMapping("/")
    public String readPostInPage(@RequestParam(name = "page", required = false, defaultValue = "0") int pageNo, @RequestParam(name = "limit", required = false, defaultValue = "5") int limit,Model model) {
        Pageable page = PageRequest.of(pageNo, limit);
        Page<PostEntity> pageWithPosts = postService.findPages(page);
        model.addAttribute("totalPages",pageWithPosts.getTotalPages());
        model.addAttribute("posts", pageWithPosts.getContent());
        return "index";
    }

    @RequestMapping("/post/read")
    public String readPost(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("postEntity", postService.findById(postId));
        model.addAttribute("commentEntity", new CommentEntity());
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

    @RequestMapping("/post/create/save")
    public String saveNewPost(@ModelAttribute("postEntity") PostEntity postEntity) {
        postEntity.setAuthor(userService.findById(1));
        postEntity.setExcerpt("something to show");
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setIsPublished(true);
        postService.save(postEntity);
        return "redirect:/";
    }

    @RequestMapping("/post/update/save")
    public String saveUpdatedPost(@ModelAttribute("postEntity") PostEntity postEntity) {
        postService.save(postEntity);
        return "redirect:/post/read?postId="+postEntity.getId();
    }
}

