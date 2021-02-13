package com.akashmjain.BlogApplication.rest;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.enitity.TagEntity;
import com.akashmjain.BlogApplication.enitity.UserEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AnonymousUserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    /* SEARCH AND FILTER SECTION */
    @GetMapping("/posts")
    public List<PostEntity> getFilteredPosts(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
                                             @RequestParam(value = "search", required = false) String search,
                                             @RequestParam(value = "tagId", required = false) List<Integer> tagIds,
                                             @RequestParam(value = "authorId", required = false) List<Integer> authorIds,
                                             @RequestParam(value = "order", required = false, defaultValue = "asc") String sortOrder,
                                             @RequestParam(value = "startDate", required = false) String startDate,
                                             @RequestParam(value = "endDate", required = false) String endDate) {
        List<PostEntity> posts;
        posts = postService.findAll();
        if (search != null ||  tagIds != null || authorIds != null) {
            posts = getFilteredPostEntityList(authorIds, tagIds, search);
        }
        System.out.println(posts);
        posts = posts.stream().filter(post -> post.getIsPublished()).collect(Collectors.toList());
        posts = getUniquePostEntityList(posts);
        posts = getPostFilteredByDate(startDate, endDate, posts);
        posts = getSortedPostEntityList(posts, sortOrder);
        posts = getPostFilteredByDate(startDate, endDate, posts);
        Page<PostEntity> pages = getPaginatedData(posts, pageNo, 4);
        return pages.getContent();
    }

    @GetMapping("/post")
    public PostEntity getPostById(@RequestParam("postId") int postId) throws Exception {
        if (postService.findById(postId) != null) {
            if (postService.findById(postId).getIsPublished())
                return postService.findById(postId);
            else {
                throw new Exception("Not published yet");
            }
        } else {
            throw new Exception("post id not found");
        }
    }

    @GetMapping("comments")
    public List<CommentEntity> getCommentsByPostId(@RequestParam("postId") int postId) throws Exception {
        if (postService.findById(postId) == null) {
            throw new Exception("Post not found");
        } else {
            return postService.findById(postId).getComments();
        }
    }

    @PostMapping("/comment/create")
    public CommentEntity createComment(@RequestBody CommentEntity commentEntity, @RequestParam("postId") int postId) throws Exception {
        if (postService.findById(postId) == null) {
            throw new Exception("Post not found");
        } else {
            commentEntity.setId(0);
            commentEntity.setPostEntity(postService.findById(postId));
            commentEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            commentService.save(commentEntity);
        }
        return commentEntity;
    }

    @GetMapping("/tags")
    public List<TagEntity> getTags() {
        return tagService.findAll();
    }

    @GetMapping("/tag")
    public TagEntity getTagById(@RequestParam("tagId") int tagId) {
        return tagService.findById(tagId);
    }

    @GetMapping("/tagpost")
    public List<TagEntity> getTagByPostId(@RequestParam("postId") int postId) {
        return postService.findById(postId).getTags();
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/user")
    public UserEntity getUserById(@RequestParam("userId") int userId) {
        return userService.findById(userId);
    }

    /*Search and Filter Helper methods*/
    private List<PostEntity> getUniquePostEntityList(List<PostEntity> posts) {
        Set<PostEntity> set = new LinkedHashSet<>(posts);
        posts.clear();
        posts.addAll(set);
        return posts;
    }

    private List<PostEntity> getSortedPostEntityList(List<PostEntity> posts, String sortOrder) {
        if(posts == null) return null;
        List<PostEntity> modifiableList = new ArrayList<PostEntity>(posts);
        if (sortOrder.toLowerCase(Locale.ROOT).equals("dsc"))
            modifiableList.sort((o2, o1) -> o1.getPublishedAt().compareTo(o2.getPublishedAt()));
        else if (sortOrder.toLowerCase(Locale.ROOT).equals("asc")) {
            modifiableList.sort((o1, o2) -> o1.getPublishedAt().compareTo(o2.getPublishedAt()));
        }
        return modifiableList;
    }

    private List<PostEntity> getFilteredPostEntityList(List<Integer> authorIds, List<Integer> tagIds, String search) {
        List<PostEntity> posts = new ArrayList<>();
        if (search != null) {
            posts.addAll(postService.getPostsBySearchString(search));
            posts.addAll(userService.getPostsByUserName(search));
            posts.addAll(tagService.getPostsByTagName(search));
        }
        if (authorIds != null) {
            posts = userService.getPostsByUserIdList(authorIds, posts);
        }
        if (tagIds != null) {
            posts = tagService.getPostsByTagIdList(tagIds, posts);
        }
        return posts;
    }

    private List<PostEntity> getPostFilteredByDate(String startDateString, String endDateString, List<PostEntity> posts) {
        ArrayList<PostEntity> filteredPosts = new ArrayList<>();
        if (posts == null) return null;
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
            for (PostEntity post : posts) {
                Date postDate = new Date(post.getPublishedAt().getTime());
                if (postDate.getTime() >= startDate.getTime() && postDate.getTime() <= endDate.getTime()) {
                    filteredPosts.add(post);
                }
            }
        } catch (Exception e) {
            return posts;
        }
        return filteredPosts;
    }

    private Page<PostEntity> getPaginatedData(List<PostEntity> posts, int pageNo, int size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        long start = pageable.getOffset();
        long end =(start + pageable.getPageSize()) > posts.size() ? posts.size() : (start + pageable.getPageSize());
        Page<PostEntity> pages = new PageImpl<PostEntity>(posts.subList((int)start, (int)end), pageable, posts.size());
        return pages;
    }
}
