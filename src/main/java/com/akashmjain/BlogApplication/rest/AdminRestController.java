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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/admin/api")
public class AdminRestController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    private final int EXCERPT_SIZE = 100;


    @GetMapping("echo")
    public String echo() {
        return "echo from ADMIN";
    }

    @PostMapping("/post/create")
    public PostEntity createNewPost(@RequestBody PostEntity postEntity,
                                    @RequestParam("authorId") int authorId,
                                    @RequestParam("tagStringData") String tagString) throws Exception {
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
        } else {
            postEntity.setPublishedAt(null);
        }
        postService.save(postEntity);
        return postEntity;
    }

    @PutMapping("/post/update")
    public PostEntity updatePost(@RequestBody PostEntity post, @RequestParam("authorId") int authorId) {
        PostEntity postEntity = postService.findById(post.getId());
        UserEntity userEntity = userService.findById(authorId);
        postEntity.setAuthor(userEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setExcerpt(post.getExcerpt());
        postEntity.setContent(post.getContent());
        postEntity.setIsPublished(post.getIsPublished());
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (postEntity.getIsPublished()) {
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        } else {
            postEntity.setPublishedAt(null);
        }
        postService.save(postEntity);
        return postEntity;
    }

    @DeleteMapping("/post/delete")
    public String deletePost(@RequestParam("postId") int postId) {
        postService.deleteById(postId);
        return "DELETE SUCCESS";
    }

    @PutMapping("/comment/update")
    public CommentEntity updateComment(@RequestBody CommentEntity comment) {
        CommentEntity commentEntity = commentService.findById(comment.getId());
        commentEntity.setName(comment.getName());
        commentEntity.setEmail(comment.getEmail());
        commentEntity.setComment(comment.getComment());
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.save(commentEntity);
        return commentEntity;
    }

    @DeleteMapping("/comment/delete")
    public String deleteComment(@RequestParam("commentId") int commentId) {
        commentService.deleteById(commentId);
        return "COMMENT DELETE SUCCESS";
    }
}
