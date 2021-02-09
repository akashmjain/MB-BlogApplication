package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
@RequestMapping("/admin")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    /* CREATE COMMENT */
    @RequestMapping("/comment/create")
    public String createComment(@RequestParam("postId") int postId, Model model) {
        PostEntity postEntity = postService.findById(postId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentEntity.setPostEntity(postEntity);
        commentEntity.setId(0);
        model.addAttribute("commentEntity", commentEntity);
        return "comment_form";
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
        return "redirect:/admin/post/read?postId=" + postId;
    }


    /* INTERNALLY CALLED */
    @RequestMapping("/comment/save")
    public String saveComment(@ModelAttribute("commentEntity") CommentEntity commentEntity) {
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.save(commentEntity);
        return "redirect:/admin/post/read?postId=" + commentEntity.getPostEntity().getId();
    }
}
