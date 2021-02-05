package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    /* CREATE COMMENT */
    @RequestMapping("/comment/save")
    public String saveComment(@ModelAttribute("commentEntity") CommentEntity commentEntity, @RequestParam("postId") int postId) {
        PostEntity postEntity = postService.findById(postId);
        commentEntity.setPost(postEntity);
        commentEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (commentEntity.getId() != 0) {
            commentEntity.setId(0);
        }
        commentService.save(commentEntity);
        return "redirect:/post/read?postId=" + postId;
    }

    /* UPDATE COMMENT */
    @RequestMapping("/comment/update")
    public String updateComment(CommentEntity commentEntity, @RequestParam("postId") int postId) {
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.save(commentEntity);
        return "redirect:/post/read?postId=" + postId;
    }

    /* DELETE COMMENT */
    @RequestMapping("/comment/delete")
    public String deleteComment(@RequestParam("postId") int postId, @RequestParam("commentId") int commentId) {
        commentService.deleteById(commentId);
        return "redirect:/post/read?postId=" + postId;
    }
}
