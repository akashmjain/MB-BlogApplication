package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    private final int EXCERPT_SIZE = 100;

    @RequestMapping("/post/create")
    public String createPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("postEntity", postEntity);
        return "write_blog";
    }

    @RequestMapping("/post/update")
    public String updatePost(@RequestParam("postId") int postId, Model model) {
        PostEntity postEntity = postService.findById(postId);
        String tagStringData = "";
        for (TagEntity tagEntity : postEntity.getTags()) {
            tagStringData += tagEntity.getName() + ",";
        }
        model.addAttribute("postEntity", postEntity);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("tagStringData", tagStringData);
        return "update_blog";
    }

    @RequestMapping("/post/create/save")
    public String saveNewPost(@ModelAttribute("postEntity") PostEntity postEntity,
                              @RequestParam("tagStringData") String tagString) {
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            String content = postEntity.getContent();
            String excerpt = content.length() > EXCERPT_SIZE ? content.substring(0, EXCERPT_SIZE) : content;
            postEntity.setAuthor(userService.findByName(username));
            postEntity.setContent(content);
            postEntity.setExcerpt(excerpt);
            postEntity.setTags(tagService.stringToTag(tagString));
            postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            postEntity.setIsPublished(true);
            postService.save(postEntity);
        } else {
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @RequestMapping("/post/update/save")
    public String saveUpdatedPost(@ModelAttribute("postEntity") PostEntity postEntity,
                                  @RequestParam("tagStringData") String tagString) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username.equals(postEntity.getAuthor().getName())) {
                postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                postEntity.setTags(tagService.stringToTag(tagString));
                postService.save(postEntity);
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
        return "redirect:/post/read?postId="+postEntity.getId();
    }

    @RequestMapping("/post/delete")
    public String deletePost(@RequestParam("postId")int postId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if(postService.findById(postId).getAuthor().getName().equals(username)) {
                postService.deleteById(postId);
            } else {
                return "redirect:/error";
            }
        }

        return "redirect:/";
    }

    /* UPDATE COMMENT */
    @RequestMapping("/comment/update")
    public String updateComment(@RequestParam("commentId") int commentId,Model model) {
        CommentEntity commentEntity = commentService.findById(commentId);
        model.addAttribute("commentEntity", commentEntity);
        return "comment_update_form";
    }

    @RequestMapping("/comment/update/save")
    public String updateComment(@ModelAttribute("commentEntity") CommentEntity commentEntity) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if(username.equals(commentEntity.getPostEntity().getAuthor().getName())) {
                commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                commentService.save(commentEntity);
            } else {
                return "redirect:/error";
            }
        }
        return "redirect:/post/read?postId=" + commentEntity.getPostEntity().getId();
    }

    /* DELETE COMMENT */
    @RequestMapping("/comment/delete")
    public String deleteComment(@RequestParam("postId") int postId, @RequestParam("commentId") int commentId) {
        CommentEntity commentEntity = commentService.findById(commentId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if(commentEntity.getPostEntity().getAuthor().getName().equals(username)) {
                commentService.deleteById(commentId);
            }
        }
        return "redirect:/post/read?postId=" + postId;
    }

}
