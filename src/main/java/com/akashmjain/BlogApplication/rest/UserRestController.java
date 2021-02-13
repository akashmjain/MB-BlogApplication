package com.akashmjain.BlogApplication.rest;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/user/api")
public class UserRestController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    private final int EXCERPT_SIZE = 100;

    @GetMapping("/echo")
    public String echo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        return "HELLO " + username;
    }

    @PostMapping("/post/create")
    public PostEntity saveNewPost(@RequestBody PostEntity postEntity,
                              @RequestParam("tagStringData") String tagString) throws Exception {
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
            if (postEntity.getIsPublished()) {
                postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            } else {
                postEntity.setPublishedAt(null);
            }
            postService.save(postEntity);
        } else {
            throw new Exception("Non logged in user");
        }
        return postEntity;
    }

    @PutMapping("/post/update")
    public PostEntity updatePost(@RequestBody PostEntity post, @RequestParam("tagStringData") String tags) throws Exception {
        PostEntity postEntity = postService.findById(post.getId());
        if (postEntity == null) {
            throw new Exception("Post with ID : " + post.getId() + " Not found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username.equals(postEntity.getAuthor().getName())) {
                postEntity.setTitle(post.getTitle());
                postEntity.setExcerpt(post.getExcerpt());
                postEntity.setContent(post.getContent());
                postEntity.setIsPublished(post.getIsPublished());
                if (postEntity.getIsPublished()) {
                    postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
                } else {
                    postEntity.setPublishedAt(null);
                }
                postService.save(postEntity);
            } else {
                throw new Exception("No Principal found");
            }
        }
        return postEntity;
    }

    @DeleteMapping("/post/delete")
    public String deletePost(@RequestParam("postId") int postId) throws Exception {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username.equals(postService.findById(postId).getAuthor().getName())) {
                postService.deleteById(postId);
                return "deletion success";
            } else {
                throw new Exception("You are not authenticated User");
            }
        } else {
            throw new Exception("Principal not valid");
        }
    }

    @PutMapping("/comment/update")
    public CommentEntity updateComment(@RequestBody CommentEntity comment) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            CommentEntity commentEntity = commentService.findById(comment.getId());
            if (commentEntity == null) {
                throw new Exception("Comment Entity with this Id is not found");
            }
            if (commentEntity.getPostEntity().getAuthor().getName().equals(username)) {
                commentEntity.setName(comment.getName());
                commentEntity.setEmail(comment.getEmail());
                commentEntity.setComment(comment.getComment());
                commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                commentService.save(commentEntity);
                return commentEntity;
            } else {
                throw new Exception("User is not authorized (its not your comment)");
            }
        } else {
            throw new Exception("Principal is not valid");
        }
    }

    @DeleteMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") int commentId) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if (username.equals(commentService.findById(commentId).getPostEntity().getAuthor().getName())) {
                commentService.deleteById(commentId);
                return "deletion success";
            } else {
                throw new Exception("User is not allowed to delete this comment");
            }
        } else {
            throw new Exception("Principal is not valid");
        }
    }
}
