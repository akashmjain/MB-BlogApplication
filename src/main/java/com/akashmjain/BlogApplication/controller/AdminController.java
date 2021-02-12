package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminTest")
public class AdminController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    private final int EXCERPT_SIZE = 100;

    /* POST SECTION */
    @PostMapping("/post/create")
    public PostEntity saveNewPost(@RequestBody PostEntity postEntity,
                              @RequestParam("tagStringData") String tagString,
                              @RequestParam("authorId") int authorId) {
        String content = postEntity.getContent();
        String excerpt = content.length() > EXCERPT_SIZE ? content.substring(0, EXCERPT_SIZE) : content;
        postEntity.setAuthor(userService.findById(authorId));
        postEntity.setContent(content);
        postEntity.setExcerpt(excerpt);
        postEntity.setTags(tagService.stringToTag(tagString));
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (postEntity.getIsPublished()) {
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        postService.save(postEntity);
        return postEntity;
    }

    @DeleteMapping("/post/delete")
    public String deletePost(@RequestParam("postId")int postId) {
        postService.deleteById(postId);
        return "Deleted Post";
    }

    @RequestMapping("/post/update")
    public PostEntity saveUpdatedPost(@RequestBody PostEntity post,
                                  @RequestParam(value = "authorId", required = false) Integer authorId,
                                  @RequestParam("tagStringData") String tagString) {
        PostEntity postEntity = postService.findById(post.getId());
        postEntity.setTitle(post.getTitle());
        postEntity.setExcerpt(post.getExcerpt());
        postEntity.setContent(post.getContent());
        postEntity.setIsPublished(post.getIsPublished());
        postEntity.setAuthor(userService.findById(authorId) == null ? postEntity.getAuthor() : userService.findById(authorId));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (postEntity.getIsPublished()) {
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        postEntity.setTags(tagService.stringToTag(tagString));
        postService.save(postEntity);
        return postEntity;
    }
    /* COMMENT SECTION */
    @RequestMapping("/comment/update")
    public String saveUpdatedComment(@ModelAttribute("commentEntity") CommentEntity commentEntity) {
        commentService.save(commentEntity);
        return "redirect:/post/read?postId="+commentEntity.getPostEntity().getId();
    }

    @RequestMapping("/comment/delete")
    public String deleteComment(@RequestParam("postId") int postId, @RequestParam("commentId") int commentId) {
        commentService.deleteById(commentId);
        return "redirect:/post/read?postId=" + postId;
    }

    /* DASHBOARD SECTION */
    @RequestMapping("/post/publish")
    public String publishPost(@RequestParam("postId") int postId) {
        PostEntity post = postService.findById(postId);
        post.setIsPublished(true);
        post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        postService.save(post);
        return "redirect:/admin/dashboard";
    }
}