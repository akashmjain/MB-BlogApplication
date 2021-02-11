package com.akashmjain.BlogApplication.controller;

import com.akashmjain.BlogApplication.enitity.CommentEntity;
import com.akashmjain.BlogApplication.enitity.PostEntity;
import com.akashmjain.BlogApplication.service.comment.CommentService;
import com.akashmjain.BlogApplication.service.post.PostService;
import com.akashmjain.BlogApplication.service.tag.TagService;
import com.akashmjain.BlogApplication.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AnonymousController {
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    private String urlData;

    @RequestMapping("/")
    public String getFilteredData(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
                                  @RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "tagId", required = false) List<Integer> tagIds,
                                  @RequestParam(value = "authorId", required = false) List<Integer> authorIds,
                                  @RequestParam(value = "order", required = false, defaultValue = "asc") String sortOrder,
                                  @RequestParam(value = "startDate", required = false) String startDate,
                                  @RequestParam(value = "endDate", required = false) String endDate,
                                  Model model) {
        urlData = "?";
        List<PostEntity> posts;
        if(search == null && tagIds == null && authorIds == null)
            posts = postService.findAll();
        else {
            posts = getFilteredPostEntityList(authorIds, tagIds, search);
        }
        posts = posts.stream().filter(post -> post.getIsPublished()).collect(Collectors.toList());
        posts = getUniquePostEntityList(posts);
        posts = getPostFilteredByDate(startDate, endDate, posts);
        posts = getSortedPostEntityList(posts, sortOrder);
        posts = getPostFilteredByDate(startDate, endDate, posts);

        Page<PostEntity> page = getPaginatedData(posts, pageNo, 4);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("posts", page.getContent());
        model.addAttribute("pathTo", urlData);
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allTags", tagService.findAll());
        return "index";
    }

    @RequestMapping("/post/read")
    public String readPost(@RequestParam("postId") int postId, Model model) {
        model.addAttribute("postEntity", postService.findById(postId));
        Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        Collection<?> gr = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            gr = ((UserDetails) principal).getAuthorities();
        } else {
            username = principal.toString();
        }
        System.out.println(gr);
        return "show_blog";
    }

    @RequestMapping("/comment/create")
    public String createComment(@RequestParam("postId") int postId, Model model) {
        PostEntity postEntity = postService.findById(postId);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentEntity.setPostEntity(postEntity);
        commentEntity.setId(0);
        model.addAttribute("commentEntity", commentEntity);
        return "comment_create_form";
    }

    @RequestMapping("/comment/create/save")
    public String saveComment(@ModelAttribute("commentEntity") CommentEntity commentEntity) {
        commentEntity.setId(0); // to make sure that Id not passed and brand new comment is created
        commentEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.save(commentEntity);
        return "redirect:/post/read?postId=" + commentEntity.getPostEntity().getId();
    }

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
            urlData += "search="+search;
        }
        if (authorIds != null) {
            posts = userService.getPostsByUserIdList(authorIds, posts);
            for(int id : authorIds) {
                urlData += "&authorId="+id;
            }
        }
        if (tagIds != null) {
            posts = tagService.getPostsByTagIdList(tagIds, posts);
            for(int id : tagIds) {
                urlData += "&tagId=" + id;
            }
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

