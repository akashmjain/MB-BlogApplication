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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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

    /* POST SECTION */
    @RequestMapping("/post/create")
    public String createPost(Model model) {
        PostEntity postEntity = new PostEntity();
        model.addAttribute("postEntity", postEntity);
        model.addAttribute("users", userService.findAll());
        return "write_blog";
    }

    @RequestMapping("/post/create/save")
    public String saveNewPost(@ModelAttribute("postEntity") PostEntity postEntity,
                              @RequestParam("tagStringData") String tagString,
                              @RequestParam("publish") boolean isPublished,
                              @RequestParam("authorId") int authorId) {
        String content = postEntity.getContent();
        String excerpt = content.length() > EXCERPT_SIZE ? content.substring(0, EXCERPT_SIZE) : content;
        postEntity.setAuthor(userService.findById(authorId));
        postEntity.setContent(content);
        postEntity.setExcerpt(excerpt);
        postEntity.setTags(tagService.stringToTag(tagString));
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setIsPublished(isPublished);
        if (isPublished) {
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        postService.save(postEntity);
        return "redirect:/";
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
        model.addAttribute("tagStringData",tagStringData);
        return "update_blog";
    }

    @RequestMapping("/post/delete")
    public String deletePost(@RequestParam("postId")int postId) {
        postService.deleteById(postId);
        return "redirect:/";
    }

    @RequestMapping("/post/update/save")
    public String saveUpdatedPost(@ModelAttribute("postEntity") PostEntity postEntity,
                                  @RequestParam(value = "authorId", required = false) Integer authorId,
                                  @RequestParam("publish") boolean isPublished,
                                  @RequestParam("tagStringData") String tagString) {
        postEntity.setAuthor(userService.findById(authorId));
        postEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        postEntity.setIsPublished(isPublished);
        if (isPublished) {
            postEntity.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        }
        postEntity.setTags(tagService.stringToTag(tagString));
        postService.save(postEntity);
        return "redirect:/post/read?postId="+postEntity.getId();
    }


    /* COMMENT SECTION */
    @RequestMapping("/comment/update")
    public String updateComment(@RequestParam("commentId") int commentId,Model model) {
        CommentEntity commentEntity = commentService.findById(commentId);
        model.addAttribute("commentEntity", commentEntity);
        return "comment_update_form";
    }

    @RequestMapping("/comment/update/save")
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
    @RequestMapping("/dashboard")
    public String userDashboard(Model model) {
        List<PostEntity> listOfPosts = postService.findAll().stream().filter(post -> !post.getIsPublished()).collect(Collectors.toList());
        model.addAttribute("posts", listOfPosts);
        return "dashboard";
    }

    @RequestMapping("/post/publish")
    public String publishPost(@RequestParam("postId") int postId) {
        PostEntity post = postService.findById(postId);
        post.setIsPublished(true);
        post.setPublishedAt(new Timestamp(System.currentTimeMillis()));
        postService.save(post);
        return "redirect:/admin/dashboard";
    }
}