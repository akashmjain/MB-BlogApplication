package com.akashmjain.BlogApplication.rest;

import com.akashmjain.BlogApplication.enitity.PostEntity;
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

    @RequestMapping("/post/create")
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
}
