package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
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

    /* CREATE SECTION */
    @RequestMapping("/post/create")
    public String createPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("postEntity", postEntity);
        model.addAttribute("tagStringData", "");
        model.addAttribute("users", userService.findAll());
        return "write_blog";
    }


    /* UPDATE SECTION */
    @RequestMapping("/post/update")
    public String updatePost(@RequestParam("postId") int postId, Model model) {
        PostEntity postEntity = postService.findById(postId);
        model.addAttribute("postEntity", postEntity);
        model.addAttribute("users", userService.findAll());
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
    public String saveUpdatedPost(@ModelAttribute("postEntity") PostEntity postEntity,@RequestParam(value = "authorId", required = false, defaultValue = "-1") int authorId) {
        if(authorId > 0) {
            postEntity.setAuthor(userService.findById(authorId));
        }
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postService.save(postEntity);
        return "redirect:/post/read?postId="+postEntity.getId();
    }

    /* UPDATE COMMENT */
    @RequestMapping("/comment/update")
    public String updateComment(@RequestParam("commentId") int commentId,Model model) {
        CommentEntity commentEntity = commentService.findById(commentId);
        model.addAttribute("commentEntity", commentEntity);
        return "comment_form";
    }

    /* DELETE COMMENT */
    @RequestMapping("/comment/delete")
    public String deleteComment(@RequestParam("postId") int postId, @RequestParam("commentId") int commentId) {
        commentService.deleteById(commentId);
        return "redirect:/post/read?postId=" + postId;
    }
}

